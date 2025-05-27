# GitHub Copilot Development Environment Customization Guide

This guide explains how to customize the GitHub Copilot coding agent's development environment for your Java Spring Boot project.

## Current Setup

Your project now has a customized Copilot environment configured in `.github/workflows/copilot-setup-steps.yml`.

### What's Included

#### Basic Environment (Current Configuration)

- **Java 21** with Temurin distribution
- **Maven** dependency management with caching
- **Pre-compilation** of source and test code
- **Test execution** to verify environment
- **Essential development tools** (curl, jq, git, tree)
- **Environment verification** steps

#### Enhanced Environment (Optional)

An enhanced example is available in `.github/workflows/copilot-setup-steps-enhanced.yml.example` with:

- **Larger runner support** (requires GitHub Team/Enterprise)
- **Advanced caching strategies**
- **Parallel compilation**
- **Code quality tools integration**
- **IDE configurations**
- **Development scripts**
- **Comprehensive documentation generation**

## Key Customization Options

### 1. Runner Configuration

**Standard Runner (Current)**

```yaml
runs-on: ubuntu-latest
```

**Larger Runners (For Better Performance)**

```yaml
runs-on: ubuntu-4-core # Requires GitHub Team/Enterprise
# Other options: ubuntu-8-core, ubuntu-16-core
```

### 2. Environment Customization

**Custom Environment Variables**

```yaml
- name: Setup environment variables
  run: |
    echo "MAVEN_OPTS=-Xmx2g -XX:+UseG1GC" >> $GITHUB_ENV
    echo "SPRING_PROFILES_ACTIVE=copilot" >> $GITHUB_ENV
```

**Custom Container**

```yaml
container:
  image: openjdk:21-jdk
  options: --user root
```

### 3. Service Dependencies

Add databases or other services for integration testing:

```yaml
services:
  postgres:
    image: postgres:15
    env:
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: testdb
    options: >-
      --health-cmd pg_isready
      --health-interval 10s
      --health-timeout 5s
      --health-retries 5

  redis:
    image: redis:7-alpine
    options: >-
      --health-cmd "redis-cli ping"
      --health-interval 10s
      --health-timeout 5s
      --health-retries 5
```

### 4. Additional Tools

**Development Tools**

```yaml
- name: Install development tools
  run: |
    sudo apt-get update
    sudo apt-get install -y \
      curl jq git tree htop vim nano \
      docker.io docker-compose \
      postgresql-client \
      redis-tools
```

**Node.js for Frontend Development**

```yaml
- name: Set up Node.js
  uses: actions/setup-node@v4
  with:
    node-version: "18"
    cache: "npm"
```

### 5. Performance Optimizations

**Advanced Maven Caching**

```yaml
- name: Advanced Maven caching
  uses: actions/cache@v4
  with:
    path: |
      ~/.m2/repository
      ~/.m2/wrapper
      target
    key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
```

**Parallel Compilation**

```yaml
- name: Parallel compilation
  run: ./mvnw -T 4 compile test-compile
```

## Project-Specific Customizations

### For Your Ticket Management System

Given your Spring Boot application with JWT, JPA, and H2, consider these additions:

**1. Database Setup**

```yaml
- name: Setup test database
  run: |
    # Pre-populate test data for better Copilot understanding
    ./mvnw liquibase:update -Dspring.profiles.active=test || true
```

**2. Security Testing Tools**

```yaml
- name: Install security testing tools
  run: |
    # Install tools for JWT testing
    npm install -g jwt-cli
```

**3. API Documentation**

```yaml
- name: Generate API documentation
  run: |
    ./mvnw springdoc-openapi:generate || echo "OpenAPI not configured"
```

## Testing Your Setup

1. **Manual Testing**: Go to your repository's Actions tab and run the "Copilot Setup Steps" workflow manually
2. **Verify Dependencies**: Check that all dependencies are cached and compilation succeeds
3. **Test Environment**: Ensure tests pass in the Copilot environment

## Common Issues and Solutions

### Issue: Maven Dependencies Not Found

**Solution**: Add explicit dependency download:

```yaml
- name: Download dependencies explicitly
  run: ./mvnw dependency:resolve dependency:resolve-sources
```

### Issue: Java Version Conflicts

**Solution**: Explicitly set JAVA_HOME:

```yaml
- name: Set Java environment
  run: |
    echo "JAVA_HOME=${{ env.JAVA_HOME }}" >> $GITHUB_ENV
    export PATH=$JAVA_HOME/bin:$PATH
```

### Issue: Tests Failing in CI

**Solution**: Use test-specific profile:

```yaml
- name: Run tests with CI profile
  run: ./mvnw test -Dspring.profiles.active=test,ci
```

## Best Practices

1. **Keep it Simple**: Start with the basic configuration and add complexity as needed
2. **Cache Everything**: Cache Maven dependencies, compiled classes, and test results
3. **Fail Fast**: Run quick validation steps early to catch issues
4. **Document Changes**: Update this guide when making modifications
5. **Test Locally**: Use `act` or similar tools to test workflows locally when possible

## Security Considerations

- Use minimal permissions in the `permissions` block
- Don't expose sensitive environment variables
- Use secrets for any credentials (though Copilot gets its own token)
- Keep dependencies up to date

## Monitoring and Maintenance

- Monitor workflow execution times
- Update Java and Maven versions regularly
- Review and update cached dependencies periodically
- Monitor for security vulnerabilities in dependencies

## Next Steps

1. **Test the current setup** by running the workflow manually
2. **Consider the enhanced setup** if you need better performance
3. **Add project-specific customizations** based on your development needs
4. **Monitor Copilot's performance** and adjust as needed

For more information, see:

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [GitHub Copilot Coding Agent Documentation](https://docs.github.com/en/copilot/using-github-copilot/coding-agent)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
