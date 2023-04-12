package fingerprintAttendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("api/v1/fingerprint")
@RestController
public class FingerprintController {
    public final FingerprintService fingerprintService;

    @Autowired
    public FingerprintController(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }

    @GetMapping("validate")
    public String validateFingerprints() throws ExecutionException, InterruptedException {
        return fingerprintService.validateFingerprint();
    }

    @GetMapping("enroll")
    public String enrollFingerprints() throws ExecutionException, InterruptedException {
        return fingerprintService.enrollFingerprint();
    }
}