package com.jaarvi.shared.capabilities

/**
 * Platform capability for secure document storage and retrieval.
 *
 * Stores travel documents (tickets, permits, reservations, etc.) securely
 * and associates them with specific activities or trips.
 *
 * V1: Basic file storage (placeholder)
 * V2 (future): Encrypted storage, cloud sync, OCR for document scanning
 */
interface DocumentVaultCapability {
    /**
     * Stores a document file securely.
     *
     * @param documentId Unique identifier for the document
     * @param fileName Original filename
     * @param fileData Binary content of the document
     * @param mimeType MIME type (e.g., "application/pdf", "image/jpeg")
     * @param metadata Optional key-value metadata (e.g., activity ID, trip ID)
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun storeDocument(
        documentId: String,
        fileName: String,
        fileData: ByteArray,
        mimeType: String,
        metadata: Map<String, String> = emptyMap()
    ): Result<Unit>
    
    /**
     * Retrieves a stored document by ID.
     *
     * @param documentId Unique identifier of the document
     * @return Result wrapping DocumentData on success or exception on failure
     */
    suspend fun getDocument(documentId: String): Result<DocumentData>
    
    /**
     * Deletes a document from secure storage.
     *
     * @param documentId Unique identifier of the document to delete
     * @return Result wrapping Unit on success or exception on failure
     */
    suspend fun deleteDocument(documentId: String): Result<Unit>
    
    /**
     * Lists all documents with optional metadata filtering.
     *
     * @param metadataFilter Optional metadata key-value pairs to filter by
     * @return Result wrapping list of DocumentInfo on success or exception on failure
     */
    suspend fun listDocuments(
        metadataFilter: Map<String, String> = emptyMap()
    ): Result<List<DocumentInfo>>
}

/**
 * Data class representing a retrieved document.
 *
 * @property documentId Unique identifier
 * @property fileName Original filename
 * @property fileData Binary content
 * @property mimeType MIME type
 * @property metadata Associated metadata
 */
data class DocumentData(
    val documentId: String,
    val fileName: String,
    val fileData: ByteArray,
    val mimeType: String,
    val metadata: Map<String, String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DocumentData

        if (documentId != other.documentId) return false
        if (fileName != other.fileName) return false
        if (!fileData.contentEquals(other.fileData)) return false
        if (mimeType != other.mimeType) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = documentId.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + fileData.contentHashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + metadata.hashCode()
        return result
    }
}

/**
 * Data class representing document metadata without the full file content.
 *
 * @property documentId Unique identifier
 * @property fileName Original filename
 * @property mimeType MIME type
 * @property sizeBytes File size in bytes
 * @property metadata Associated metadata
 */
data class DocumentInfo(
    val documentId: String,
    val fileName: String,
    val mimeType: String,
    val sizeBytes: Long,
    val metadata: Map<String, String>
)
