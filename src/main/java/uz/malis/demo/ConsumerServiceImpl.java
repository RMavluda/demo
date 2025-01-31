package uz.malis.demo;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.malis.demo.dto.CheckConsumerDto;
import uz.malis.demo.dto.CheckConsumerResponseDto;
import uz.malis.demo.dto.ConsumerDto;
import uz.malis.demo.dto.ConsumerResponseDtoMAIN;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

  private final ConsumersFeignClient consumersFeignClient;
  private final ConsumerResponseMapper mapper;

  @Override
  public CheckConsumerResponseDto check(String coatoCode, String personalAccount) {
    HashMap<String, String> headers = new HashMap<>();

    headers.put("Coato-Code", "26287");
    headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeEtsZld2b3R1YXZLMmwwUHdoYUtMb29RT0RvaUlkamFyMXlDRWJCWTVFIn0.eyJleHAiOjE3MTYwMTY0MzgsImlhdCI6MTcxNTE1MjQzOCwianRpIjoiOTE0MjAyN2EtZGQxYy00YWQ1LWFkNDEtZjRkNTlhZTdhYzRiIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMjUuNTc6ODA4MC9yZWFsbXMvSGV0S2V5Y2xvYWsiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiNWQwYmY3OWQtMDJiNi00NjQ1LWFhYzMtMGQxMjhmMTcyMGNjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiaGV0LWxvZ2luIiwic2Vzc2lvbl9zdGF0ZSI6IjA4ZDY2MzJjLWU0NDUtNDk1Yi1iMGIxLWU3OGMyZjdjMzVlMCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiYXBwLWFkbWluIiwiZGVmYXVsdC1yb2xlcy1oZXRrZXljbG9hayJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiaGV0LWxvZ2luIjp7InJvbGVzIjpbInJlYWxtLWN1c3RvbS1yb2xlIiwidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcGhvbmUgcHJvZmlsZSIsInNpZCI6IjA4ZDY2MzJjLWU0NDUtNDk1Yi1iMGIxLWU3OGMyZjdjMzVlMCIsInVwbiI6ImFkbWluIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJhZGRyZXNzIjp7fSwidXNlcl9pZCI6IjE4IiwiY29hdG9fY29kZSI6IjAwMDAwIiwibmFtZSI6IlN1cGVyIGFkbWluIC0iLCJncm91cHMiOlsiMTAwMDEiLCIxMDAxMCIsInN1cGVyLWFkbWluIiwiYXBwLWFkbWluIiwiZGVmYXVsdC1yb2xlcy1oZXRrZXljbG9hayJdLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiIsImdpdmVuX25hbWUiOiJTdXBlciBhZG1pbiIsImZhbWlseV9uYW1lIjoiLSJ9.H0lh4g7tXr6OuhzVla8fIwzJtI9FJdcAWijnOzeINYgPaHD3kRLxHcG5IH23ZL43HwKCNc8K3DM5Fx_vhNW1EJFgBvrz3VGHMGPLhybrsL10mh2bI_mKfbkevgGX8uhXLW1fluHu25GJR3yoG2u0EKK0tc58JbGjqRm17TshnUm_YPyAY9z_OlE9SfeNC4Sn6KqKUw3K5sq1XlZNxQJ3Nvrx7IEQMFSkVPd5gXtbq-Bx9OJeOmIYY3fKj4BRZ7uzgrviaE7iDlJ3Y-syrK4pUmbxfzbm-s0P9zfM_N4S_OHZaqKdOL6cE3UTlb8clKnHtTUuG80xViyd8SIsYnrnkQ");

    CheckConsumerDto checkConsumerFeignResponse = consumersFeignClient.checkConsumer(personalAccount, coatoCode, headers);

    CheckConsumerResponseDto responseDto = mapper.fromFeignResponse(checkConsumerFeignResponse);

    return responseDto;
  }

  @Override
  public ConsumerDto findConsumer(String coatoCode, Long consumerId) {

    HashMap<String, String> headers = new HashMap<>();

    headers.put("coatoCode", "26287");
    headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeEtsZld2b3R1YXZLMmwwUHdoYUtMb29RT0RvaUlkamFyMXlDRWJCWTVFIn0.eyJleHAiOjE3MTYwMTY0MzgsImlhdCI6MTcxNTE1MjQzOCwianRpIjoiOTE0MjAyN2EtZGQxYy00YWQ1LWFkNDEtZjRkNTlhZTdhYzRiIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMjUuNTc6ODA4MC9yZWFsbXMvSGV0S2V5Y2xvYWsiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiNWQwYmY3OWQtMDJiNi00NjQ1LWFhYzMtMGQxMjhmMTcyMGNjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiaGV0LWxvZ2luIiwic2Vzc2lvbl9zdGF0ZSI6IjA4ZDY2MzJjLWU0NDUtNDk1Yi1iMGIxLWU3OGMyZjdjMzVlMCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiYXBwLWFkbWluIiwiZGVmYXVsdC1yb2xlcy1oZXRrZXljbG9hayJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiaGV0LWxvZ2luIjp7InJvbGVzIjpbInJlYWxtLWN1c3RvbS1yb2xlIiwidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcGhvbmUgcHJvZmlsZSIsInNpZCI6IjA4ZDY2MzJjLWU0NDUtNDk1Yi1iMGIxLWU3OGMyZjdjMzVlMCIsInVwbiI6ImFkbWluIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJhZGRyZXNzIjp7fSwidXNlcl9pZCI6IjE4IiwiY29hdG9fY29kZSI6IjAwMDAwIiwibmFtZSI6IlN1cGVyIGFkbWluIC0iLCJncm91cHMiOlsiMTAwMDEiLCIxMDAxMCIsInN1cGVyLWFkbWluIiwiYXBwLWFkbWluIiwiZGVmYXVsdC1yb2xlcy1oZXRrZXljbG9hayJdLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiIsImdpdmVuX25hbWUiOiJTdXBlciBhZG1pbiIsImZhbWlseV9uYW1lIjoiLSJ9.H0lh4g7tXr6OuhzVla8fIwzJtI9FJdcAWijnOzeINYgPaHD3kRLxHcG5IH23ZL43HwKCNc8K3DM5Fx_vhNW1EJFgBvrz3VGHMGPLhybrsL10mh2bI_mKfbkevgGX8uhXLW1fluHu25GJR3yoG2u0EKK0tc58JbGjqRm17TshnUm_YPyAY9z_OlE9SfeNC4Sn6KqKUw3K5sq1XlZNxQJ3Nvrx7IEQMFSkVPd5gXtbq-Bx9OJeOmIYY3fKj4BRZ7uzgrviaE7iDlJ3Y-syrK4pUmbxfzbm-s0P9zfM_N4S_OHZaqKdOL6cE3UTlb8clKnHtTUuG80xViyd8SIsYnrnkQ");

    ConsumerResponseDtoMAIN result = consumersFeignClient.findConsumer(consumerId, coatoCode, headers);

    var streetName = result.getStreet().getNameUz();
    var habitationDivision = result.getHabitationDivision().getNameUz();
    var buildingNumber = result.getBuildingNumber();
    var flatNumber = result.getFlatNumber();
    var corpusNumber = result.getCorpusNumber();

    ConsumerDto consumerDto = new ConsumerDto();
    consumerDto.setId(result.getId());
    consumerDto.setPersonalAccount(result.getPersonalAccount());

    consumerDto.setAddress(streetName + " " + habitationDivision +
        " " + buildingNumber + " " + flatNumber + " " + corpusNumber);
    consumerDto.setCoatoCode(result.getCoatoCode());
    consumerDto.setFullName(result.getConsumerDetail().getFullName());
    consumerDto.setLastReadingPlus(result.getMeter().getLastReadingPlus());
    consumerDto.setLastReadingDate(result.getMeter().getLastReadingDate());

    return consumerDto;
  }
}
