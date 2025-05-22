# Usage Guide

This guide explains how to use the Event Ticket Management System's core features.

## Logging In

1. Navigate to the login page at `http://localhost:8080/login`
2. Enter your username and password
3. Click "Login"

![Login Screen](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/login_screen.png)

## Dashboard Overview

After logging in, you'll see the main dashboard with:

- Event summary cards
- Quick access to ticket search
- Navigation menu for other features

![Dashboard](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/dashboard.png)

## Searching for Tickets

### Method 1: Quick Search

1. Use the search box in the header
2. Enter the unique ticket code
3. Press Enter or click the search icon

### Method 2: Advanced Search

1. Navigate to "Tickets" → "Search Tickets"
2. Fill in search criteria (ticket code, status, event)
3. Click "Search"
4. View results in the table below

![Ticket Search](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/ticket_search.png)

## Viewing Ticket Details

1. Find the ticket using search
2. Click on the ticket row or the "View Details" button
3. Review the ticket information on the details page

![Ticket Details](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/ticket_details.png)

## Updating Ticket Status

1. Navigate to the ticket details page
2. Click the "Change Status" button
3. Select the new status from the dropdown (e.g., "Active" or "Used")
4. Click "Update"
5. Verify the status change notification

![Update Ticket Status](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/update_status.png)

## Browsing Events

1. Click on "Events" in the navigation menu
2. View the list of all events
3. Use filters to narrow down the results

![Events List](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/events_list.png)

## Viewing Event Tickets

1. Navigate to the Events list
2. Click on an event name or the "View Tickets" button
3. See all tickets associated with the event
4. Use filters to sort or find specific tickets

![Event Tickets](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/event_tickets.png)

## Logging Out

1. Click on your username in the top-right corner
2. Select "Logout" from the dropdown menu
3. You will be redirected to the login page

## Administrative Functions

These features are available only to users with admin privileges:

### User Management

1. Navigate to "Admin" → "User Management"
2. View existing users
3. Create, edit, or deactivate user accounts

### System Settings

1. Navigate to "Admin" → "Settings"
2. Configure application settings
3. Save changes

## Keyboard Shortcuts

The application supports the following keyboard shortcuts:

- `Alt + S`: Focus search box
- `Alt + D`: Go to dashboard
- `Alt + E`: Go to events
- `Alt + T`: Go to tickets
- `Alt + H`: Show help overlay

## Best Practices

- **Regular Updates**: Make sure to update ticket statuses promptly
- **Search Efficiency**: Use ticket codes for the fastest search results
- **Session Security**: Always log out when you're finished
- **Data Verification**: Double-check ticket information before making changes