import React, { useEffect, useState } from "react";
import {
  Container,
  Box,
  Grid,
  Card,
  CardMedia,
  CardActionArea,
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
  Button,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import PokemonDetailsCard from "./PokemonDetailsCard";
import NavigationBar from "./MenuAppBar";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";

interface StoredPokemon {
  id: number;
  name: string;
  data: string;
}

interface ParsedPokemon {
  id: number;
  name: string;
  height: number;
  weight: number;
  abilities: any[];
  stats: any[];
  types: any[];
  sprites: {
    front_default?: string;
    [key: string]: any;
  };
}

const AlbumPage: React.FC = () => {
  const [album, setAlbum] = useState<StoredPokemon[]>([]);
  const [selected, setSelected] = useState<ParsedPokemon | null>(null);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const fetchAlbum = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/pokemon/album",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setAlbum(response.data);
    } catch {
      setAlbum([]);
    }
  };

  const handleDelete = async (name: string) => {
    const confirmDelete = window.confirm(
      `Möchtest du ${name.toUpperCase()} wirklich aus dem Album entfernen?`
    );
    if (!confirmDelete) return;

    await axios.delete(
      `http://localhost:8080/api/pokemon/album/delete?name=${name}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    fetchAlbum();
  };

  useEffect(() => {
    fetchAlbum();
  }, []);

  return (
    <>
      <NavigationBar />

      <Button
        onClick={() => navigate("/search")}
        startIcon={<ChevronLeftIcon />}
      >
        Zurück zur Suche
      </Button>

      <Container>
        <Box mt={4}>
          <Grid container spacing={2}>
            {album.map((p, index) => {
              let parsed: ParsedPokemon;
              try {
                parsed = JSON.parse(p.data);
              } catch {
                return null;
              }

              return (
                <Grid item xs={6} sm={4} md={3} key={p.id}>
                  <Card
                    sx={{
                      position: "relative",
                      transition: "transform 0.2s",
                      "&:hover": {
                        transform: "scale(1.05)",
                        cursor: "pointer",
                      },
                    }}
                  >
                    <CardActionArea onClick={() => setSelected(parsed)}>
                      <CardMedia
                        component="img"
                        image={parsed.sprites?.front_default ?? ""}
                        alt={parsed.name}
                        sx={{
                          objectFit: "contain",
                          height: 200,
                          backgroundColor: "#f0f0f0",
                        }}
                      />
                    </CardActionArea>
                    <Button
                      onClick={() => handleDelete(p.name)}
                      size="small"
                      variant="contained"
                      color="error"
                      sx={{
                        position: "absolute",
                        top: 8,
                        right: 8,
                        padding: "2px 6px",
                        minWidth: 0,
                        fontSize: 12,
                      }}
                    >
                      ✕
                    </Button>
                  </Card>
                </Grid>
              );
            })}
          </Grid>
        </Box>

        <Dialog
          open={!!selected}
          onClose={() => setSelected(null)}
          maxWidth="md"
          fullWidth
        >
          <DialogTitle
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            {selected?.name.toUpperCase()}
            <IconButton onClick={() => setSelected(null)}>
              <CloseIcon />
            </IconButton>
          </DialogTitle>
          <DialogContent>
            {selected && (
              <PokemonDetailsCard
                name={selected.name}
                id={selected.id}
                height={selected.height}
                weight={selected.weight}
                abilities={selected.abilities}
                stats={selected.stats}
                types={selected.types}
                sprite={selected.sprites?.front_default ?? ""}
              />
            )}
          </DialogContent>
        </Dialog>
      </Container>
    </>
  );
};

export default AlbumPage;
