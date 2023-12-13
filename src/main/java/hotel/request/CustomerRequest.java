package hotel.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private String requestType;
    private String content;
}