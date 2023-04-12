package fingerprintAttendance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fingerprint {
    private String documentId;
    private String fingerHex;
}
