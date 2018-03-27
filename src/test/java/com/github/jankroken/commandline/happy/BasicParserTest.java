package com.github.jankroken.commandline.happy;

import org.junit.jupiter.api.Test;

import static com.github.jankroken.commandline.CommandLineParser.parse;
import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static org.assertj.core.api.Assertions.assertThat;


public class BasicParserTest {

    @Test
    public void testSimpleConfiguration() throws Exception {
        final var args = new String[]{"-f", "hello.txt", "-v"};
        final var config = parse(SimpleConfiguration.class, args, SIMPLE);
        assertThat(config.getFilename()).isEqualTo("hello.txt");
        assertThat(config.getVerbose()).isTrue();
    }

    @Test
    public void testMultipleArgsConfiguration() throws Exception {
        final var args = new String[]{"-files", "hello.txt", "world.txt", "bye.txt", "-logfile", "hello.log"};
        final var config = parse(MultipleArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getFiles()).containsExactly("hello.txt", "world.txt", "bye.txt");
        assertThat(config.getLogfile()).isEqualTo("hello.log");
    }

    @Test
    public void testDelimiterConfiguration() throws Exception {
        final var args = new String[]{"-exec", "ls", "-l", "*.txt", ";", "-logfile", "hello.log"};
        final var config = parse(DelimiterConfiguration.class, args, SIMPLE);
        assertThat(config.getCommand()).containsExactly("ls", "-l", "*.txt");
        assertThat(config.getLogfile()).isEqualTo("hello.log");
    }

    @Test
    public void testMultipleConfiguration() throws Exception {
        final var args = new String[]{"-verbose", "-file", "hello.txt", "-file", "world.txt"};
        final var config = parse(MultipleConfiguration.class, args, SIMPLE);
        assertThat(config.getFiles()).containsExactly("hello.txt", "world.txt");
        assertThat(config.getVerbose()).isTrue();
    }

    @Test
    public void testSubConfiguration() throws Exception {
        final var args = new String[]{"-verbose", "-album", "-name", "Caustic Grip", "-artist", "Front Line Assembly", "-year", "1990", "-available", "-logfile", "hello.log"};
        final var config = parse(SimpleSuperConfiguration.class, args, SIMPLE);
        final var album = config.getAlbum();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getVerbose()).isTrue();
        assertThat(album.getName()).isEqualTo("Caustic Grip");
        assertThat(album.getArtist()).isEqualTo("Front Line Assembly");
        assertThat(album.getYear()).isEqualTo("1990");
        assertThat(album.isAvailable()).isTrue();
    }

    @Test
    public void testMultipleSubConfiguration() throws Exception {
        final var args = new String[]{"-verbose",
                "-album", "-name", "Caustic Grip", "-artist", "Front Line Assembly", "-year", "1990", "-available",
                "-album", "-name", "Scintilla", "-artist", "Stendeck", "-year", "2011", "-available",
                "-logfile", "hello.log"};
        final var config = parse(MultipleSubconfigsConfiguration.class, args, SIMPLE);
        final var verbose = config.getVerbose();
        final var albums = config.getAlbums();
        assertThat(albums.size()).isEqualTo(2);
        final var causticGrip = albums.get(0);
        final var scintilla = albums.get(1);
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
        final var args = new String[]{"-verbose", "zombies", "ate", "my", "-logfile", "hello.log", "raptors"};
        final var config = parse(LooseArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getVerbose()).isTrue();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getArgs()).containsExactly("zombies", "ate", "my", "raptors");
    }

    @Test
    public void testArgumentEscape() throws Exception {
        final var args = new String[]{"-verbose", "-logfile", "hello.log", "--", "-zombies", "-ate", "-my", "--", "-raptors"};
        final var config = parse(LooseArgsConfiguration.class, args, SIMPLE);
        assertThat(config.getVerbose()).isTrue();
        assertThat(config.getLogfile()).isEqualTo("hello.log");
        assertThat(config.getArgs()).containsExactly("-zombies", "-ate", "-my", "--", "-raptors");
    }


}
