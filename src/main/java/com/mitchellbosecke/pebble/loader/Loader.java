/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Copyright (c) 2014 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.loader;

import java.io.Reader;

import com.mitchellbosecke.pebble.error.LoaderException;

/**
 * Interface used to find templates for Pebble. Different implementations can
 * use different techniques for finding templates such as looking on the
 * classpath, looking in a database, using a servlet context, etc.
 * 
 * @author mbosecke
 *
 */
public interface Loader {

    /**
     * The reader which will be used by Pebble to read the contents of the
     * template.
     * 
     * @param templateName
     *            Name of the template
     * @return A reader object
     * @throws LoaderException
     *             If template can not be found
     */
    public Reader getReader(String templateName) throws LoaderException;

    /**
     * A method for end users to change the charset used by the loader.
     * 
     * @param charset
     *            Character set used by the loader when building a reader object
     */
    public void setCharset(String charset);

    /**
     * Optional prefix to help find templates, ex "/WEB-INF/templates/" or
     * "database_schema."
     * 
     * @param prefix
     *            Prefix to help find templates
     */
    public void setPrefix(String prefix);

    /**
     * Optional suffix to help find templates, ex ".html", ".peb"
     * 
     * @param suffix
     *            Suffix to attach to template names
     */
    public void setSuffix(String suffix);

}
