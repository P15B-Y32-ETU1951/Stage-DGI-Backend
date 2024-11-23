import React from 'react';
import { Navigate } from 'react-router-dom';

// Composant pour les routes protégées
const ProtectedRoute = ({ children }) => {
  const isAuthenticated = () => {
    // Exemple de vérification avec le token JWT dans localStorage
    return localStorage.getItem('token') !== null;
  };

  // Si l'utilisateur est authentifié, rendre les enfants du composant
  // Sinon, rediriger vers la page de connexion
  return isAuthenticated() ? children : <Navigate to="/login" />;
};

export default ProtectedRoute;
