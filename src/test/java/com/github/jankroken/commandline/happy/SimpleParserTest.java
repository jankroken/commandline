package com.github.jankroken.commandline.happy;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.jankroken.commandline.CommandLineParser.parse;
import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static org.assertj.core.api.Assertions.assertThat;


public class SimpleParserTest {

    @Test
    public void testSimpleConfiguration() throws Exception {
        String[] args = new String[]{"-f", "hello.txt", "-v"};
        SimpleConfiguration config = parse(SimpleConfiguration.class, args, SIMPLE);
        assertThat(config.getFilename()).isEqualTo("hello.txt");
        assertThat(config.getVerbose()).isTrue();
    }

    @Test
    public void testMultipleArgsConfiguration() throws Exception {
        String[] args = new String[]{"-files", "hello.txt", "world.txt", "bye.txt", "-logfile", "hello.log"};
        MultipleArgsConfiguration config = parse(MultipleArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getFiles()).containsExactly("hello.txt", "world.txt", "bye.txt");
        assertThat(config.getLogfile()).isEqualTo("hello.log");
    }

    @Test
    public void testDelimiterConfiguration() throws Exception {
        String[] args = new String[]{"-exec", "ls", "-l", "*.txt", ";", "-logfile", "hello.log"};
        DelimiterConfiguration config = parse(DelimiterConfiguration.class, args, SIMPLE);
        assertThat(config.getCommand()).containsExactly("ls", "-l", "*.txt");
        assertThat(config.getLogfile()).isEqualTo("hello.log");
    }

    @Test
    public void testMultipleConfiguration() throws Exception {
        String[] args = new String[]{"-verbose", "-file", "hello.txt", "-file", "world.txt"};
        MultipleConfiguration config = parse(MultipleConfiguration.class, args, SIMPLE);
        assertThat(config.getFiles()).containsExactly("hello.txt", "world.txt");
        assertThat(config.getVerbose()).isTrue();
    }

    @Test
    public void testSubConfiguration() throws Exception {
        String[] args = new String[]{"-verbose", "-album", "-name", "Caustic Grip", "-artist", "Front Line Assembly", "-year", "1990", "-available", "-logfile", "hello.log"};
        SimpleSuperConfiguration config = parse(SimpleSuperConfiguration.class, args, SIMPLE);
        AlbumConfiguration album = config.getAlbum();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getVerbose()).isTrue();
        assertThat(album.getName()).isEqualTo("Caustic Grip");
        assertThat(album.getArtist()).isEqualTo("Front Line Assembly");
        assertThat(album.getYear()).isEqualTo("1990");
        assertThat(album.isAvailable()).isTrue();
    }

    @Test
    public void testMultipleSubConfiguration() throws Exception {
        String[] args = new String[]{"-verbose",
                "-album", "-name", "Caustic Grip", "-artist", "Front Line Assembly", "-year", "1990", "-available",
                "-album", "-name", "Scintilla", "-artist", "Stendeck", "-year", "2011", "-available",
                "-logfile", "hello.log"};
        MultipleSubconfigsConfiguration config = parse(MultipleSubconfigsConfiguration.class, args, SIMPLE);
        boolean verbose = config.getVerbose();
        List<AlbumConfiguration> albums = config.getAlbums();
        assertThat(albums.size()).isEqualTo(2);
        AlbumConfiguration causticGrip = albums.get(0);
        AlbumConfiguration scintilla = albums.get(1);
        assertThat(causticGrip.getName()).isEqualTo("Caustic Grip");
        assertThat(causticGrip.getArtist()).isEqualTo("Front Line Assembly");
        assertThat(causticGrip.getYear()).isEqualTo("1990");
        assertThat(causticGrip.isAvailable()).isTrue();
        assertThat(scintilla.getName()).isEqualTo("Scintilla");
        assertThat(scintilla.getArtist()).isEqualTo("Stendeck");
        assertThat(scintilla.getYear()).isEqualTo("2011");
        assertThat(scintilla.isAvailable()).isTrue();

        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(verbose).isTrue();
    }

    @Test
    public void testLooseArguments() throws Exception {
        String[] args = new String[]{"-verbose", "zombies", "ate", "my", "-logfile", "hello.log", "raptors"};
        LooseArgsConfiguration config = parse(LooseArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getVerbose()).isTrue();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getArgs()).containsExactly("zombies", "ate", "my", "raptors");
    }

    @Test
    public void testArgumentEscape() throws Exception {
        String[] args = new String[]{"-verbose", "-logfile", "hello.log", "--", "-zombies", "-ate", "-my", "--", "-raptors"};
        LooseArgsConfiguration config = parse(LooseArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getVerbose()).isTrue();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getArgs()).containsExactly("-zombies", "-ate", "-my", "--", "-raptors");
    }

    @Test
    public void testEscapePresendence() throws Exception {
        String[] args = new String[]{"-exec", "ls", "--", "*.txt", ";", "-logfile", "hello.log"};
        DelimiterConfiguration config = parse(DelimiterConfiguration.class, args, SIMPLE);
        assertThat(config.getCommand()).containsExactly("ls", "--", "*.txt");
        assertThat(config.getLogfile()).isEqualTo("hello.log");
    }

}
