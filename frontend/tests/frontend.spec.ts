import { test, expect } from "@playwright/test";

test("User kann sich registrieren, einloggen, Pokémon suchen, zum Album hinzufügen und löschen", async ({ page }) => {
  console.log("Starte Test");
  const username = `Test${Math.floor(Math.random() * 10000)}`;
  const password = "Str0ng!Password123";

  await page.goto("http://localhost:5173/login");
  console.log("Besuchte Login-Seite:", page.url());
  await page.screenshot({ path: "1-login.png" });

  await page.getByRole("link", { name: "Jetzt registrieren" }).click();
  console.log("Besuchte Registrierungsseite:", page.url());
  await page.screenshot({ path: "2-register.png" });

  await page.getByLabel("Vorname").fill("Test");
  console.log("Vorname eingetragen");

  await page.getByLabel("Nachname").fill("User");
  console.log("Nachname eingetragen");

  await page.getByLabel("Benutzername").fill(username);
  console.log("Benutzername eingetragen:", username);

  const pw = page.locator('input[name="password"]');
  const pwConfirm = page.locator('input[name="confirmPassword"]');
  await pw.fill(password);
  console.log("Passwort eingetragen");

  await pwConfirm.fill(password);
  console.log("Passwort wiederholen eingetragen");

  await Promise.all([
    page.waitForURL("**/login", { timeout: 5000 }),
    page.getByRole("button", { name: "Registrieren" }).click(),
  ]);
  console.log("Registrierungsformular abgeschickt");
  console.log("Weiter auf Login-Seite:", page.url());
  await page.screenshot({ path: "3-after-register.png" });

  await page.getByLabel("Benutzername").fill(username);
  await page.getByRole("textbox", { name: "Passwort" }).fill(password);
  console.log("Login-Daten eingetragen");

  await page.getByRole("button", { name: "Einloggen" }).click();
  console.log("Login 1. Versuch abgeschickt");

  await page.waitForTimeout(1000);
  await page.reload();
  console.log("Wieder auf Login-Seite nach 1. Login");

  page.once("dialog", async (dialog) => {
    console.log("Warnung beim Login:", dialog.message());
    await dialog.dismiss();
  });

  await page.getByRole("button", { name: "Einloggen" }).click();
  await page.waitForTimeout(1000);
  await page.reload();
  console.log("Wieder auf Login-Seite nach 1. Login");
  await page.screenshot({ path: "4-after-reload.png" });

  await page.getByLabel("Benutzername").fill(username);
  await page.getByRole("textbox", { name: "Passwort" }).fill(password);
  console.log("Login-Daten erneut eingegeben");

  await Promise.all([
    page.waitForURL("**/search", { timeout: 5000 }),
    page.getByRole("button", { name: "Einloggen" }).click(),
  ]);
  console.log("Weitergeleitet auf:", page.url());
  await page.screenshot({ path: "5-after-login.png" });

  const searchInput = page.getByRole("textbox", { name: "Pokémon suchen..." });
  await searchInput.waitFor({ timeout: 5000 });
  await searchInput.fill("pichu");
  await page.getByRole("button", { name: "Suchen" }).click();
  console.log("Nach Pokémon gesucht");
  await page.screenshot({ path: "6-after-search.png" });

  page.once("dialog", (dialog) => dialog.dismiss().catch(() => {}));
  await page.getByRole("button", { name: "Pokémon gefunden" }).click();
  console.log("Pokémon hinzugefügt");

  await page.getByRole("button", { name: "Zum Album" }).click();
  console.log("Im Album:", page.url());
  await page.screenshot({ path: "7-album.png" });

  await page.getByRole("button", { name: "pichu" }).click();
  console.log("Pokémon geöffnet");

  page.once("dialog", async (dialog) => {
    console.log("Dialog:", dialog.message());
    await dialog.dismiss().catch(() => {});
  });

  const dialogCloseBtn = page.getByRole("button").first();
  await dialogCloseBtn.waitFor({ state: "visible", timeout: 5000 });
  await dialogCloseBtn.click();
  console.log("Dialog geschlossen");

  const deleteBtn = page.getByRole("button", { name: "✕" });
  await deleteBtn.waitFor({ state: "attached", timeout: 10000 });
  await deleteBtn.waitFor({ state: "visible", timeout: 10000 });
  await deleteBtn.click();
  console.log("Pokémon gelöscht");

  await page.getByRole("button", { name: "Zurück zur Suche" }).click();
  console.log("Zurück auf Suche:", page.url());
  await page.screenshot({ path: "8-back-to-search.png" });

  await page.getByRole("button", { name: "Logout" }).click();
  console.log("Erfolgreich ausgeloggt auf:", page.url());
  await page.screenshot({ path: "9-logout.png" });
});
