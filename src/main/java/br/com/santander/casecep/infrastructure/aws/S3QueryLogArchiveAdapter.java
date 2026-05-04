package br.com.santander.casecep.infrastructure.aws;

import br.com.santander.casecep.domain.CepQueryLog;
import br.com.santander.casecep.domain.port.QueryLogArchivePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
@ConditionalOnProperty(name = "aws.archive.enabled", havingValue = "true")
public class S3QueryLogArchiveAdapter implements QueryLogArchivePort {
    private final S3Client s3Client;
    private final String bucket;

    public S3QueryLogArchiveAdapter(S3Client s3Client, @Value("${aws.archive.s3-bucket}") String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public void archive(CepQueryLog log) {
        String key = "cep-logs/%s-%s.json".formatted(
                log.getCepConsultado(),
                log.getDataHoraConsulta().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String payload = """
                {"cep":"%s","timestamp":"%s","status":%d,"payload":%s}
                """.formatted(log.getCepConsultado(), log.getDataHoraConsulta(), log.getStatusHttpExterno(), quote(log.getPayloadRetorno()));

        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).contentType("application/json").build(),
                RequestBody.fromBytes(payload.getBytes(StandardCharsets.UTF_8)));
    }

    private String quote(String value) { return '"' + value.replace("\"", "\\\"") + '"'; }
}
