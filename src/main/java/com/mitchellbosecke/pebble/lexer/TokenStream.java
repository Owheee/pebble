/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Original work Copyright (c) 2009-2013 by the Twig Team
 * Modified work Copyright (c) 2013 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.lexer;

import java.util.ArrayList;
import java.util.Collection;

import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.lexer.Token.Type;

public class TokenStream {

	private ArrayList<Token> tokens = new ArrayList<>();
	private int current;
	private String filename;

	/**
	 * Constructor for a Token Stream
	 * 
	 * @param tokens
	 *            A collection of tokens
	 * @param name
	 *            The filename of the template that these tokens came from
	 */
	public TokenStream(Collection<Token> tokens, String name) {
		this.tokens.addAll(tokens);
		this.current = 0;
		this.filename = name;
	}

	public void injectTokens(Collection<Token> tokens) {
		this.tokens.addAll(current + 1, tokens);
	}

	/**
	 * Consumes and returns the next token in the stream.
	 * 
	 * @return The next token
	 */
	public Token next() {
		return tokens.get(++current);
	}

	/**
	 * Checks the current token to see if it matches the provided type. If it
	 * doesn't match this will throw a SyntaxException. This will consume a
	 * token.
	 * 
	 * @param type
	 *            The type of token that we expect
	 * @return The current token
	 * @throws ParserException
	 */
	public Token expect(Token.Type type) throws ParserException {
		return expect(type, null);
	}

	/**
	 * Checks the current token to see if it matches the provided type. If it
	 * doesn't match this will throw a SyntaxException. This will consume a
	 * token.
	 * 
	 * @param type
	 *            The type of token that we expect
	 * @return The current token
	 * @throws ParserException
	 */
	public Token expect(Token.Type type, String value) throws ParserException {
		Token token = tokens.get(current);

		boolean success = true;
		String message = null;
		if (value == null) {
			success = token.test(type);
			if (!success && message == null) {
				message = "Unexpected token of value [" + token.getValue() + "] expected token of type " + type;
			}
		} else {
			success = token.test(type, value);
			if (!success && message == null) {
				message = "Unexpected token of value [" + token.getValue() + "] expected [" + value + "] ";
			}
		}
		if (!success) {
			throw new ParserException(null, message, token.getLineNumber(), filename);
		}
		this.next();
		return token;
	}

	/**
	 * Returns the next token in the stream without consuming it.
	 * 
	 * @return The next token
	 */
	public Token peek() {
		return peek(1);
	}

	/**
	 * Returns a future token in the stream without consuming any.
	 * 
	 * @param number
	 *            How many tokens to lookahead
	 * @return The token we are peeking at
	 */
	public Token peek(int number) {
		return this.tokens.get(this.current + number);
	}

	public boolean isEOF() {
		return this.tokens.get(current).getType().equals(Type.EOF);
	}

	@Override
	public String toString() {
		return tokens.toString();
	}

	public Token current() {
		return this.tokens.get(current);
	}

	public String getFilename() {
		return filename;
	}

	/**
	 * used for testing purposes
	 * 
	 * @return
	 */
	public ArrayList<Token> getTokens() {
		return tokens;
	}
}