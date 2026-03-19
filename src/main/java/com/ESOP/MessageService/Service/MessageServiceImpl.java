package com.ESOP.MessageService.Service;

import com.ESOP.MessageService.Dto.AppResponseDto;
import com.ESOP.MessageService.Dto.MessageDto;
import com.ESOP.MessageService.Entity.Template;
import com.ESOP.MessageService.Repository.MessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.MapperBuilder;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MessageServiceImpl implements MessageService {
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;
    private final JdbcTemplate jdbcTemplate;


    public MessageServiceImpl(ObjectMapper objectMapper,
                              MapperBuilder<?, ?> mapperBuilder,
                              MessageRepository messageRepository,
                              JdbcTemplate jdbcTemplate) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
        this.jdbcTemplate = jdbcTemplate;
        mapperBuilder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Override
    public AppResponseDto processSendSms(MessageDto messageDto) {
        try {
            Template data = messageRepository.findById(messageDto.getTemplateId()).orElseThrow();
            Map<String, Object> template = getMapFromJson(data.getPlaceHolder());

            List<String> nameList = getNameList(data.getEntityName(), String.valueOf(template.get("name")));

            List<Map<String, Object>> messageList = nameList.stream().map(message -> {
                Map<String, Object> map = new HashMap<>();
                map.put("subject", data.getSubject().replace("[name]", message));
                map.put("Body", data.getBody());
                return map;
            }).toList();

            return new AppResponseDto("200", "SUCCESS", null, messageList);

        } catch (Exception e) {
            return new AppResponseDto("500", "ERROR", e.getMessage(), null);
        }
    }

    @Override
    public AppResponseDto ProcessCreateTemplate(List<Map<String, Object>> templateList) {
        try {
            List<Template> processedTemplates = templateList.stream()
                    .map(data -> {
                        Template template = new Template();

                        // Generate a random positive BigInteger
                        long randomLong = ThreadLocalRandom.current().nextLong(1000, Long.MAX_VALUE);
                        template.setAltKey(BigInteger.valueOf(Math.abs(randomLong)));

                        // Safe string conversion
                        template.setSubject(String.valueOf(data.getOrDefault("subject", "")));
                        template.setBody(String.valueOf(data.getOrDefault("body", "")));
                        template.setEntityName(String.valueOf(data.getOrDefault("entityName", "")));

                        // Cast and convert the placeholder Map
                        //Map<String, Object> placeholders = (Map<String, Object>) data.get("placeHolder");
                        Map<String, Object> placeholders = objectMapper.convertValue(
                                data.get("placeHolder"),
                                new TypeReference<>() {
                                }
                        );
                        template.setPlaceHolder(generateMapToJson(placeholders));

                        return template;
                    })
                    .toList();
            return new AppResponseDto("200", "Success", null, messageRepository.saveAll(processedTemplates));
        } catch (Exception e) {
            return new AppResponseDto("500", "FAILED", e.getMessage(), "{}");
        }
    }

    private Map<String, Object> getMapFromJson(String jsonString) {
        //convert json to java object
        return objectMapper.readValue(jsonString, new TypeReference<>() {
        });
    }

    private String generateMapToJson(Map<String, Object> stringList) {
        //convert javaObject to json
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, stringList);
        return stringWriter.toString();
    }
    private List<String> getNameList(String entityName, String coloumnName) {
        String sql = "select coloumnName from tableName";
        sql = sql.replace("tableName", entityName).replace("coloumnName", coloumnName);
        return jdbcTemplate.queryForList(sql, String.class);
    }


//    @Override
////    @JsonIgnoreProperties(ignoreUnknown = true) to ignore Unknown properties from json
//    public AppResponseDto processSendSms(MessageDto messageDto) {
//        //to ignore Unknown properties from json file
//        mapperBuilder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Template data = messageRepository.findById(messageDto.getTemplateId()).orElseThrow();
//
//        // String jsonString = "{\"name\":\"John\", \"age\":30, \"city\":\"New York\"}";
//
//        Map<String, Object> template = getMapFromJson(data.getPlaceHolder());
////        String jsonFormate=generateMapToJson(template);
////        System.out.println(jsonFormate);
//        System.out.println(template);
//       code standarad , readable , reusable, unneccassay funcion not need
//        return null;
//    }

}
