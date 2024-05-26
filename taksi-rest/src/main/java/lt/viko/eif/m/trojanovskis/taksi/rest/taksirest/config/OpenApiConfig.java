package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Mantas Trojanovskis",
                        email = "mantas.trojanovskis@eif.viko.lt",
                        url= "eif.viko.lt"),
                description = "This is a REST API for a taxi company",
                title = "Taksi REST API",
                version = "1.0.0",
                license = @License (
                        name = "Free To Use",
                        url = "eif.viko.lt")

        ),
        servers = {
                @Server(
                        url = "http://localhost:8090",
                        description = "Local DEV"
                ),

                @Server(
                        url = "http://localhost:8090",
                        description = "TEST Server"
                )
        }
)
public class OpenApiConfig {
}
