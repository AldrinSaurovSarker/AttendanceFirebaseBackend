package fingerprintAttendance;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootApplication
public class AttendanceApplication {
	public static void main(String[] args) throws IOException {
		InputStream serviceAccount = AttendanceApplication.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://attendance-system-9cb22-default-rtdb.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);


		SpringApplication.run(AttendanceApplication.class, args);
	}

}
