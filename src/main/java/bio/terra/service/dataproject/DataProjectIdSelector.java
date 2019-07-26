package bio.terra.service.dataproject;

import bio.terra.metadata.Dataset;
import bio.terra.metadata.FSObjectBase;
import bio.terra.metadata.Study;

public interface DataProjectIdSelector {

    String projectIdForStudy(Study study);

    String projectIdForDataset(Dataset dataset);

    String projectIdForFile(FSObjectBase file);
}
