package com.feelhub.domain.cloudinary;

import com.feelhub.domain.DomainException;

public class CloudinaryException extends DomainException {
    public CloudinaryException(Exception innerException) {
        super(innerException);
    }

    public CloudinaryException(String message) {
        super(message);
    }

    public CloudinaryException(String message, Exception innerException) {
        super(message, innerException);
    }
}
