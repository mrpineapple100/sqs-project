
import React from "react";
import {
  Card,
  CardContent,
  Typography,
  Box,
  Chip,
  Grid,
  Divider,
} from "@mui/material";

interface Ability {
  ability: { name: string };
  is_hidden: boolean;
}

interface Stat {
  base_stat: number;
  stat: { name: string };
}

interface Type {
  type: { name: string };
}

interface Props {
  name: string;
  id: number;
  height: number;
  weight: number;
  abilities: Ability[];
  stats: Stat[];
  types: Type[];
  sprite: string;
}

const PokemonDetailsCard: React.FC<Props> = ({
  name,
  id,
  height,
  weight,
  abilities,
  stats,
  types,
  sprite,
}) => (
  <Card sx={{ mt: 4, p: 2 }}>
    <CardContent>
      <Box display="flex" gap={4}>
        <img src={sprite} alt={name} style={{ width: 120 }} />
        <Box>
          <Typography variant="h5">{name.toUpperCase()} (#{id})</Typography>
          <Typography>Größe: {height / 10} m</Typography>
          <Typography>Gewicht: {weight / 10} kg</Typography>
          <Typography mt={1}>Typen:</Typography>
          <Box display="flex" gap={1} flexWrap="wrap">
            {types.map((t) => (
              <Chip key={t.type.name} label={t.type.name} />
            ))}
          </Box>
        </Box>
      </Box>
      <Divider sx={{ my: 2 }} />
      <Typography variant="subtitle1">Fähigkeiten:</Typography>
      <Box display="flex" gap={1} flexWrap="wrap" mb={2}>
        {abilities.map((a, i) => (
          <Chip key={i} label={`${a.ability.name}${a.is_hidden ? ' (versteckt)' : ''}`} />
        ))}
      </Box>
      <Typography variant="subtitle1">Statuswerte:</Typography>
      <Grid container spacing={1}>
        {stats.map((s, i) => (
          <Grid item xs={6} sm={4} key={i}>
            <Typography variant="body2">
              {s.stat.name}: {s.base_stat}
            </Typography>
          </Grid>
        ))}
      </Grid>
    </CardContent>
  </Card>
);

export default PokemonDetailsCard;
