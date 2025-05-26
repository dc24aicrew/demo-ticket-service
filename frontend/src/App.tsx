import React, { useState, useEffect } from 'react';

const App: React.FC = () => {
  const [status, setStatus] = useState<string>('Loading...');

  useEffect(() => {
    // Display a simple status message
    setTimeout(() => {
      setStatus('Event Ticket Management System Frontend (Development Mode)');
    }, 1000);

    // In a real app, you could make API calls here
    // For example: fetch('/api/status').then(...)
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col justify-center items-center p-4">
      <div className="max-w-md w-full bg-white rounded-lg shadow-md p-6">
        <h1 className="text-2xl font-bold text-center text-gray-800 mb-4">
          Event Ticket Management System
        </h1>
        <div className="bg-blue-50 p-4 rounded-md mb-4">
          <p className="text-blue-700">{status}</p>
        </div>
        <p className="text-gray-600 text-center">
          This is a placeholder frontend for the demo ticket service.
        </p>
      </div>
    </div>
  );
};

export default App;