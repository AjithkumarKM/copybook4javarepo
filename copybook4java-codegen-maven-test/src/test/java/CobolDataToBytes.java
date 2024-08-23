import com.nordea.oss.copybook.CopyBookSerializer;
import mypackage.HospitalTest;

public class CobolDataToBytes {
    public static void main(String[] args) {
        // Define the sample data with fixed lengths
        String hospitalData = String.format("%-20s%-30s%-10s%-20s",
                "City Hospital", "123 Main St, Anytown, USA", "1234567890", "John Doe");

        String wardData = String.format("%-2s%-3s%-3s%-3s%-20s",
                "01", "100", "200", "050", "General Ward");

        String patientData = String.format("%-20s%-30s%-10s%-4s%-6s%-1s%-20s%-4s%-30s",
                "Jane Smith", "456 Elm St, Anytown, USA", "0987654321", "B101", "082024", "N", "None", "0000", "None");

        String symptomData = String.format("%-20s%-6s%-1s%-20s%-20s%-10s",
                "Flu", "081924", "N", "Rest and hydration", "Dr. Adams", "1234567890");

        String treatmentData = String.format("%-20s%-6s%-20s%-30s%-1s%-6s%-30s",
                "Medication", "081924", "Antiviral", "Increase fluids and rest", "N", "000000", "None");

        String doctorData = String.format("%-20s%-30s%-10s%-20s",
                "Dr. Adams", "789 Oak St, Anytown, USA", "1234567890", "General Medicine");

        String facilityData = String.format("%-20s%-3s%-3s",
                "MRI", "005", "002");

        // Combine all data into a single string
        String combinedData = hospitalData + wardData + patientData + symptomData + treatmentData + doctorData + facilityData;

        // Convert the combined string to a byte array using ASCII encoding
        byte[] byteArray = combinedData.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        CopyBookSerializer respoCopyBookSerializer=new CopyBookSerializer(HospitalTest.class);
        HospitalTest response = respoCopyBookSerializer.deserialize(byteArray, HospitalTest.class);

        // Print the byte array
        for (byte b : byteArray) {
            System.out.print((char) b);
        }
    }
}