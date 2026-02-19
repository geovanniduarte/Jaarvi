import Foundation
import shared

/**
 * iOS implementation of DocumentVaultCapability.
 * V1: Placeholder for future document storage (tickets, permits, etc.)
 * V2: Will implement secure local storage with Keychain
 */
class IOSDocumentVaultCapability: DocumentVaultCapability {
    
    func storeDocument(
        documentId: String,
        fileName: String,
        fileData: KotlinByteArray,
        mimeType: String,
        metadata: [String: String]
    ) async throws {
        throw NSError(
            domain: "IOSDocumentVaultCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Document storage not yet implemented"]
        )
    }
    
    func getDocument(documentId: String) async throws -> DocumentData {
        throw NSError(
            domain: "IOSDocumentVaultCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Document retrieval not yet implemented"]
        )
    }
    
    func deleteDocument(documentId: String) async throws {
        throw NSError(
            domain: "IOSDocumentVaultCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Document deletion not yet implemented"]
        )
    }
    
    func listDocuments(metadataFilter: [String: String]) async throws -> [DocumentInfo] {
        throw NSError(
            domain: "IOSDocumentVaultCapability",
            code: -1,
            userInfo: [NSLocalizedDescriptionKey: "Document listing not yet implemented"]
        )
    }
}
