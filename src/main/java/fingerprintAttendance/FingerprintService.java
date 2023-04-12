package fingerprintAttendance;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class FingerprintService {
    public String validateFingerprint() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference fingerprintRef = firestore.collection("Fingerprints").document("temp");
        ApiFuture<DocumentSnapshot> apiFuture = fingerprintRef.get();
        DocumentSnapshot document = apiFuture.get();

        if (!document.exists()) {
            return "No fingerprint found on the sensor";
        }

        String fingerHex = document.getString("fingerHex");
        if (fingerHex == null || fingerHex.isEmpty()) {
            return "Invalid fingerprint data";
        }

        Query query = firestore.collection("Fingerprints").whereEqualTo("fingerHex", fingerHex);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        firestore.collection("Fingerprints").document("temp").delete();

        if (documents.size() > 1) {
            return "Fingerprint matched.";
        } else {
            return "Fingerprint doesn't match.";
        }
    }

    public String enrollFingerprint() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference fingerprintRef = firestore.collection("Fingerprints").document("temp");
        ApiFuture<DocumentSnapshot> apiFuture = fingerprintRef.get();
        DocumentSnapshot document = apiFuture.get();

        if (!document.exists()) {
            return "No fingerprint found on the sensor";
        }

        String fingerHex = document.getString("fingerHex");
        if (fingerHex == null || fingerHex.isEmpty()) {
            return "Invalid fingerprint data";
        }

        Query query = firestore.collection("Fingerprints").whereEqualTo("fingerHex", fingerHex);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        if (documents.size() > 1) {
            fingerprintRef.delete();
            return "Fingerprint already exists in the database";
        }

        int count = firestore.collection("Fingerprints").get().get().size();
        String documentId = "fingerprint" + count;
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setDocumentId(documentId);
        fingerprint.setFingerHex(fingerHex);

        ApiFuture<WriteResult> collectionsApiFuture = firestore.collection("Fingerprints").
                document(fingerprint.getDocumentId()).set(fingerprint);
        collectionsApiFuture.get();

        ApiFuture<WriteResult> deleteResult = fingerprintRef.delete();
        deleteResult.get();

        return "Fingerprint enrolled successfully";
    }
}
