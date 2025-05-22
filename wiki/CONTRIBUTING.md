# Contributing to the Wiki

This guide explains how to contribute to and maintain the Event Ticket Management System Wiki documentation.

## Wiki Structure

The Wiki is organized into the following sections:

- **[Home](Home)** - Overview and navigation
- **[Getting Started](Getting-Started)** - Setup instructions and quick start guide
- **[Architecture](Architecture)** - Technical architecture and design patterns
- **[Usage](Usage)** - How to use the application's features
- **[API Reference](API-Reference)** - API documentation
- **[FAQ](FAQ)** - Frequently asked questions

## How to Contribute

### Editing Wiki Pages

1. Clone the wiki repository:
   ```bash
   git clone https://github.com/dc24aicrew/demo-ticket-service.wiki.git
   ```

2. Make your changes to the Markdown files.

3. Commit and push your changes:
   ```bash
   git add .
   git commit -m "Update wiki: [brief description of changes]"
   git push
   ```

### Adding Images

1. Place images in the `images` directory within the wiki repository.
2. Follow the image guidelines in [images/README.md](images/README.md).
3. Reference images in wiki pages using the format:
   ```markdown
   ![Alt Text](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/image_name.png)
   ```

### Creating New Pages

1. Create a new Markdown file in the wiki repository with a descriptive name.
2. Add a link to the new page from relevant existing pages.
3. Follow the established formatting and style conventions.

## Maintaining the Wiki

### When to Update the Wiki

Update the Wiki when:

1. **New Features** are added to the application
2. **APIs change** or are extended
3. **Architecture** is modified
4. **Setup instructions** change
5. **Common issues** are identified that should be documented in the FAQ

### Syncing with Code Changes

To keep the Wiki in sync with code changes:

1. **Review Pull Requests** - For each PR that introduces user-facing changes or API modifications, add a task to update the relevant Wiki pages.
2. **Version Documentation** - When applicable, include version information for features (e.g., "Available since v1.2").
3. **Code Reviews** - Include Wiki updates as part of the code review checklist.

### Documentation Standards

- Use **Markdown** for all Wiki content.
- Maintain a **consistent style** across pages.
- Include **screenshots** for UI-related features.
- Keep **API documentation** up-to-date with the latest endpoints.
- Organize content with clear **headings and lists**.
- Use **code blocks** with language specification for code examples.

## Wiki Review Process

Periodically review the Wiki:

1. **Quarterly Review** - Conduct a full review of all Wiki pages.
2. **Release Updates** - Before each release, ensure the Wiki reflects the new features.
3. **Link Checking** - Verify internal and external links are working.
4. **Image Verification** - Ensure all images are up-to-date and properly displayed.

## Getting Help

If you have questions about Wiki contributions:

- Ask in the project's GitHub Discussions
- Contact the documentation team lead
- Reference this contribution guide