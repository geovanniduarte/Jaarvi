package com.jaarvi.android.capabilities

import android.content.Context
import com.jaarvi.shared.capabilities.DocumentData
import com.jaarvi.shared.capabilities.DocumentInfo
import com.jaarvi.shared.capabilities.DocumentVaultCapability

/**
 * Android implementation of DocumentVaultCapability.
 * V1: Placeholder for future document storage (tickets, permits, etc.)
 * V2: Will implement secure local storage with encryption
 *
 * @property context Android application context
 */
class AndroidDocumentVaultCapability(
    private val context: Context
) : DocumentVaultCapability {

    override suspend fun storeDocument(
        documentId: String,
        fileName: String,
        fileData: ByteArray,
        mimeType: String,
        metadata: Map<String, String>
    ): Result<Unit> {
        return Result.failure(UnsupportedOperationException("Document storage not yet implemented"))
    }

    override suspend fun getDocument(documentId: String): Result<DocumentData> {
        return Result.failure(UnsupportedOperationException("Document retrieval not yet implemented"))
    }

    override suspend fun deleteDocument(documentId: String): Result<Unit> {
        return Result.failure(UnsupportedOperationException("Document deletion not yet implemented"))
    }

    override suspend fun listDocuments(
        metadataFilter: Map<String, String>
    ): Result<List<DocumentInfo>> {
        return Result.failure(UnsupportedOperationException("Document listing not yet implemented"))
    }
}
