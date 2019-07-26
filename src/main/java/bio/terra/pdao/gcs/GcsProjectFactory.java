package bio.terra.pdao.gcs;

import bio.terra.filesystem.ProjectAndCredential;
import com.google.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GcsProjectFactory {
    private final GcsConfiguration gcsConfiguration;
    private final HashMap<ProjectAndCredential, GcsProject> gcsProjectLookup = new HashMap<>();

    @Autowired
    public GcsProjectFactory(GcsConfiguration gcsConfiguration) {
        this.gcsConfiguration = gcsConfiguration;
    }

    public GcsProject get(String projectId, Credentials credentials) {
        ProjectAndCredential projectAndCredential = new ProjectAndCredential(projectId, credentials);
        if (!gcsProjectLookup.containsKey(projectAndCredential)) {
            GcsProject gcsProject;
            if (credentials == null) {
                gcsProject = new GcsProject(projectId,
                    gcsConfiguration.getConnectTimeoutSeconds(),
                    gcsConfiguration.getReadTimeoutSeconds());
            } else {
                gcsProject = new GcsProject(projectId,
                    credentials,
                    gcsConfiguration.getConnectTimeoutSeconds(),
                    gcsConfiguration.getReadTimeoutSeconds());
            }
            gcsProjectLookup.put(projectAndCredential, gcsProject);
        }
        return gcsProjectLookup.get(projectAndCredential);
    }

    public  GcsProject get(String projectId) {
        return get(projectId, null);
    }
}
