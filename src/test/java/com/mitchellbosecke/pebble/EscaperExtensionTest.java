/*******************************************************************************
 * Copyright (c) 2013 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class EscaperExtensionTest extends AbstractTest {

	@Test
	public void testEscapeHtml() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble.compile("{{ '&<>\"\\'/' | escape }}");

		Writer writer = new StringWriter();
		template.evaluate(writer);
		assertEquals("&amp;&lt;&gt;&quot;&#x27;&#x2F;", writer.toString());
	}
	
	@Test
	public void testEscapeWithNamedArguments() throws PebbleException, IOException {
		Loader loader = new StringLoader();
		PebbleEngine pebble = new PebbleEngine(loader);

		PebbleTemplate template = pebble.compile("{{ '&<>\"\\'/' | escape(strategy='html') }}");

		Writer writer = new StringWriter();
		template.evaluate(writer);
		assertEquals("&amp;&lt;&gt;&quot;&#x27;&#x2F;", writer.toString());
	}
}