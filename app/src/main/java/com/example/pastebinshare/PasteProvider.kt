package com.example.pastebinshare

/** A service that can host a chunk of text and hand back a URL for it. */
interface PasteProvider {

    /**
     * Uploads [text] and returns the public URL of the created paste.
     *
     * @param expiryDays number of days the paste should be kept.
     * @throws PasteException if the service rejected or did not answer the upload.
     */
    suspend fun upload(text: String, expiryDays: Int): String
}

/** A failed upload, with [message] suitable for showing to the user. */
class PasteException(message: String, cause: Throwable? = null) : Exception(message, cause)
