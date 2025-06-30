import React, { useState } from "react";
import { TextField, Button, Container, Typography, Box } from "@mui/material";
import axios, { AxiosError } from "axios";
import { useNavigate } from "react-router-dom";

const RegisterForm = () => {
  const [form, setForm] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    firstName: "",
    lastName: "",
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (form.password !== form.confirmPassword) {
      setMessage("Passwörter stimmen nicht überein.");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/auth/register", {
        username: form.username,
        password: form.password,
        firstName: form.firstName,
        lastName: form.lastName,
      });
      navigate("/login");
    } catch (error) {
      const err = error as AxiosError;
      if (err.response) {
        setMessage(`Fehler ${err.response.status}: ${err.response.data}`);
      } else {
        setMessage("Unbekannter Fehler bei der Registrierung.");
      }
    }
  };

  return (
    <Container maxWidth="xs">
      <Box mt={8}>
        <Typography variant="h5" align="center" gutterBottom>
          Registrieren
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Vorname"
            name="firstName"
            margin="normal"
            value={form.firstName}
            onChange={handleChange}
            required
          />
          <TextField
            fullWidth
            label="Nachname"
            name="lastName"
            margin="normal"
            value={form.lastName}
            onChange={handleChange}
            required
          />
          <TextField
            fullWidth
            label="Benutzername"
            name="username"
            margin="normal"
            value={form.username}
            onChange={handleChange}
            required
          />
          <TextField
            fullWidth
            label="Passwort"
            name="password"
            type="password"
            margin="normal"
            value={form.password}
            onChange={handleChange}
            required
          />
          <TextField
            fullWidth
            label="Passwort wiederholen"
            name="confirmPassword"
            type="password"
            margin="normal"
            value={form.confirmPassword}
            onChange={handleChange}
            required
          />
          <Box mt={2}>
            <Button fullWidth type="submit" variant="contained" color="primary">
              Registrieren
            </Button>
          </Box>
        </form>
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

export default RegisterForm;
