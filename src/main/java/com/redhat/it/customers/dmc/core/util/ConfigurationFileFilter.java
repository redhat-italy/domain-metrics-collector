/**
 * 
 */
package com.redhat.it.customers.dmc.core.util;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;

import com.redhat.it.customers.dmc.core.constants.Constants;

/**
 * @author Andrea Battaglia
 *
 */
public class ConfigurationFileFilter implements Filter<Path> {

    /**
     * @see java.nio.file.DirectoryStream.Filter#accept(java.lang.Object)
     */
    @Override
    public boolean accept(Path entry) throws IOException {
        return (!Files.isDirectory(entry))
                && (entry.toString().toLowerCase()
                        .endsWith(Constants.CONFIGURATION_FILE_EXTENSION
                                .getValue()));
    }

}
