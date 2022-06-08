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
@Document(collection = "User_Time_Stamp")
public class UserTimeStamp {

    @Id
    private String id;

    @Field(value = "Date_Time")
    private LocalDateTime timeStamp;

    @Field(value = "User_Name")
    private String userName;
}
