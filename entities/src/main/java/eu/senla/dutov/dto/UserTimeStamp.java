package eu.senla.dutov.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@Document(collection = UserTimeStamp.USER_TIME_STAMP)
public class UserTimeStamp {

    private static final String USER_NAME = "User_Name";
    private static final String DATE_TIME = "Date_Time";
    public static final String USER_TIME_STAMP = "User_Time_Stamp";

    @Id
    private String id;

    @Field(value = DATE_TIME)
    private LocalDateTime timeStamp;

    @Field(value = USER_NAME)
    private String userName;
}
