import React, { useState } from "react";
import { Box, Button, Container, InputBase, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import PokemonDetailsCard from "./PokemonDetailsCard";
import NavigationBar from "./MenuAppBar";

interface PokemonResult {
  name: string;
  id: number;
  height: number;
  weight: number;
  abilities: any[];
  stats: any[];
  types: any[];
  sprites: { front_default: string };
}

const SearchPage: React.FC = () => {
  const [query, setQuery] = useState("");

  const [result, setResult] = useState<PokemonResult | string | null>(null);
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  const handleSearch = async () => {
    const name = query.trim().toLowerCase();
    if (!name) {
      setResult("Bitte gib einen Pokémon-Namen ein.");
      return;
    }
    try {
      const response = await axios.get(
        `http://localhost:8080/api/pokemon/search?name=${name}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setResult(response.data);
    } catch {
      setResult("Pokémon nicht gefunden.");
    }
  };

  const handleAdd = async () => {
    const name = query.trim().toLowerCase();
    await axios.post(
      `http://localhost:8080/api/pokemon/album/add?name=${name}`,
      {},
      { headers: { Authorization: `Bearer ${token}` } }
    );
    alert("Zum Album hinzugefügt.");
  };

  return (
    <>
      <NavigationBar />

      <Box mt={2} px={2} sx={{ display: "flex", justifyContent: "center" }}>
        <Button variant="contained" onClick={() => navigate("/album")}>
          Zum Album
        </Button>
      </Box>

      <Container>
        <Box mt={4} display="flex" gap={2}>
          <InputBase
            placeholder="Pokémon suchen..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            sx={{
              flexGrow: 1,
              border: "1px solid #ccc",
              borderRadius: 1,
              px: 2,
            }}
          />
          <Button
            variant="contained"
            onClick={handleSearch}
            disabled={!query.trim()}
          >
            Suchen
          </Button>
        </Box>

        {result && typeof result === "object" && (
          <>
            <PokemonDetailsCard
              name={result.name}
              id={result.id}
              height={result.height}
              weight={result.weight}
              abilities={result.abilities}
              stats={result.stats}
              types={result.types}
              sprite={result.sprites.front_default}
            />
            <Box mt={2}>
              <Button variant="outlined" onClick={handleAdd}>
                Pokémon gefunden
              </Button>
            </Box>
          </>
        )}

        {typeof result === "string" && (
          <Box mt={4}>
            <Typography variant="body1">{result}</Typography>
          </Box>
        )}
      </Container>
    </>
  );
};

export default SearchPage;
