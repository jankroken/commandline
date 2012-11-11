package commandline.domain;

import java.util.Iterator;

public interface Tokenizer extends Iterator<Token> {

    public void setArgumentTerminator(String argumentTerminator);

    public boolean hasNext();

    public Token peek();

    public Token next();

    public void remove();
}