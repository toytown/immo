package com.oas.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.util.io.Streams;

public abstract class StreamImageResource extends DynamicImageResource implements Serializable{


	public StreamImageResource(Locale locale) {
		super(locale);
	}

	public StreamImageResource(String format, Locale locale) {
		super(format, locale);
	}

	
    @Override
    protected byte[] getImageData() {
    	
    	InputStream in = getImageInputStream();
    	
        if (in != null ) {
        	ByteArrayOutputStream out = new ByteArrayOutputStream();
        	try {
				Streams.copy(in, out);
			} catch (IOException e) {
				throw new WicketRuntimeException("Error while reading image data", e);
			}

        	return out.toByteArray();
        } else {
            return new byte[0];
        }

    }
	
    public abstract InputStream getImageInputStream();

    
}
