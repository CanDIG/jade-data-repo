package bio.terra.filesystem;

import com.google.auth.Credentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class FireStoreProject {
    private static final Logger logger = LoggerFactory.getLogger(FireStoreProject.class);
    private static HashMap<ProjectAndCredential, FireStoreProject> fireStoreProjectLookup = new HashMap<>();
    private String projectId;
    private Firestore firestore;
    public FireStoreProject(String projectId) {
        logger.info("Retrieving firestore project for project id: {}", projectId);
        this.projectId = projectId;
        firestore = FirestoreOptions.newBuilder()
            .setProjectId(projectId)
            .build()
            .getService();
    }

    public FireStoreProject(String projectId, Credentials credentials) {
        this.projectId = projectId;
        firestore = FirestoreOptions.newBuilder()
            .setProjectId(projectId)
            .setCredentials(credentials)
            .build()
            .getService();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Firestore getFirestore() {
        return firestore;
    }

    public void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    public static FireStoreProject get(String projectId, Credentials credentials) {
        ProjectAndCredential projectAndCredential = new ProjectAndCredential(projectId, credentials);
        if (!fireStoreProjectLookup.containsKey(projectAndCredential)) {
            FireStoreProject fireStoreProject = new FireStoreProject(projectId);
            fireStoreProjectLookup.put(projectId, fireStoreProject);
        }
        return fireStoreProjectLookup.get(projectId);
    }

    private class ProjectAndCredential{
        private String projectId;
        private Credentials credentials;

        public ProjectAndCredential(String projectId, Credentials credentials) {
            this.credentials = credentials;
            this.projectId = projectId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProjectAndCredential)) return false;
            ProjectAndCredential projectAndCredential = (ProjectAndCredential) o;
            return projectAndCredential.credentials==credentials && projectAndCredential.projectId == projectId;
        }

        @Override
        public int hashCode() {
            int result = projectId.hashCode();

            if (credentials != null) {
                result += credentials.hashCode()*31;
            }

            return result;
        }
    }
}
