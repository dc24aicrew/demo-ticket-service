# Lombok Usage Guide

This document provides guidance on using Lombok in this project.

## What is Lombok?

[Project Lombok](https://projectlombok.org/) is a Java library that automatically plugs into your editor and build tools, spicing up your Java. It reduces boilerplate code by automatically generating commonly used code patterns through annotations.

## Configuration

Our project has Lombok configured in the following ways:

1. **Dependency**: Lombok is included as a Maven dependency in `pom.xml`
2. **IDE Support**: VS Code has Lombok support enabled via extensions and settings
3. **Custom Configuration**: Project-specific settings are defined in `lombok.config`

## Key Annotations

### Class-Level Annotations

| Annotation | Description |
|------------|-------------|
| `@Data` | Generates getters, setters, equals(), hashCode(), toString() - a shortcut for @Getter @Setter @EqualsAndHashCode @ToString |
| `@Value` | Immutable version of @Data - all fields are final, setters are not generated |
| `@Builder` | Implements the Builder pattern for object creation |
| `@NoArgsConstructor` | Generates a constructor with no parameters |
| `@AllArgsConstructor` | Generates a constructor with one parameter for each field |
| `@RequiredArgsConstructor` | Generates a constructor for all `final` fields |

### Field-Level Annotations

| Annotation | Description |
|------------|-------------|
| `@Getter` | Generates a getter method for the field |
| `@Setter` | Generates a setter method for the field |
| `@NonNull` | Generates null checks for the field |
| `@With` | Generates "with" methods for immutable objects (like withName()) |

### Method-Level Annotations

| Annotation | Description |
|------------|-------------|
| `@SneakyThrows` | Allows throwing checked exceptions without declaring them |
| `@Synchronized` | Synchronized wrapper for methods (safer than synchronized keyword) |
| `@Cleanup` | Automatically calls close() at the end of a scope |

### Other Useful Annotations

| Annotation | Description |
|------------|-------------|
| `@Slf4j` | Creates a logger field (SLF4J) |
| `@Log4j2` | Creates a logger field (Log4j2) |
| `@UtilityClass` | Makes a class a utility class |

## Best Practices

1. **Use `@Data` for simple DTOs** - Perfect for request/response objects
2. **Use `@Value` for immutable objects** - Great for domain objects that shouldn't change
3. **Use `@Builder` with `@AllArgsConstructor`** - Provides flexibility in object creation
4. **Use `@Slf4j` for logging** - Consistent logging across the application
5. **Combine with validation annotations** - Works well with Jakarta Validation

## Examples

### Basic DTO

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
```

### Immutable Domain Object

```java
@Value
@Builder
public class Product {
    Long id;
    String name;
    BigDecimal price;
}
```

### Entity with Builder

```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    private String email;
}
```

### Controller with Logging

```java
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.info("Fetching all users");
        // Implementation
    }
}
```

## Troubleshooting

1. **Compilation Issues**: Ensure Lombok plugin is installed and enabled in your IDE
2. **Missing Methods**: Check that you're using the right annotations for your use case
3. **Configuration Problems**: Review the lombok.config file for any custom settings

## Resources

- [Official Lombok Documentation](https://projectlombok.org/features/all)
- [Lombok on GitHub](https://github.com/projectlombok/lombok)
