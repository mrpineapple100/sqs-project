import React, { useState } from "react";
import { TextField, Button, Container, Typography, Box } from "@mui/material";
import axios, { AxiosError } from "axios";
import { useNavigate, Link } from "react-router-dom";

const Login: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post<{ token: string }>(
        "http://localhost:8080/api/auth/login",
        {
          username,
          password,
        }
      );
      localStorage.setItem("token", response.data.token);
      window.location.replace('/search');
    } catch (error) {
      const err = error as AxiosError;
      if (err.response) {
        setMessage(`Fehler ${err.response.status}: ${err.response.data}`);
      } else {
        setMessage("Login fehlgeschlagen.");
      }
    }
  };

  return (
    <Container maxWidth="xs">
      <Box mt={8} textAlign="center">
        <Typography variant="h4" gutterBottom sx={{ fontWeight: 700 }}>
          PokeSearch
        </Typography>
        <img
          src="/uploads/logo.png"
          alt="Pokeball"
          style={{ width: 80, height: 80, marginBottom: 16 }}
        />
        <Typography variant="h5" gutterBottom>
          Login
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Benutzername"
            margin="normal"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <TextField
            fullWidth
            label="Passwort"
            type="password"
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <Box mt={2}>
            <Button fullWidth type="submit" variant="contained" color="primary">
              Einloggen
            </Button>
          </Box>
        </form>
        <Box mt={2}>
          <Typography variant="body2">
            Noch kein Konto? <Link to="/register">Jetzt registrieren</Link>
          </Typography>
        </Box>
        {message && (
          <Box mt={2}>
            <Typography variant="body2" color="error">
              {message}
            </Typography>
          </Box>
        )}
      </Box>
    </Container>
  );
};

export default Login;
