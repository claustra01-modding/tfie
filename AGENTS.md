# AGENTS.md

## Project overview

TFIE is a small NeoForge compatibility addon between TerraFirmaCraft (TFC)
and Immersive Engineering (IE) for Minecraft 1.21.1.

Keep the addon focused. Add only the integration needed by the requested
feature; do not copy unrelated systems from the reference project.

The main reference implementation is:

- https://github.com/NMagpie/TFC-IE-Crossover/tree/1.21.x

Code or assets adapted from that project must retain the MIT attribution in
`THIRD_PARTY_NOTICES.md`, and the notice must remain included in the built JAR.

## Build and validation

- Use Java 21.
- Validate changes with `./gradlew build`.
- Do not launch a client or server (`runClient`, `runServer`, or equivalent)
  unless the user explicitly asks for it.
- Do not run data generation unless a change specifically requires it.
- Before handing off, run `git diff --check` in addition to the build.
- Do not commit generated `build/`, `run/`, IDE, or Gradle cache files.

## Dependencies

- NeoForge, Minecraft, TFC, IE, and Patchouli versions are defined in
  `gradle.properties`.
- Keep mod dependency declarations in
  `src/main/templates/META-INF/neoforge.mods.toml` synchronized with Gradle.
- Prefer the same TFC and IE versions used by the 1.21.x reference unless the
  user requests an upgrade.
- Do not add optional content mods merely because the reference project uses
  them.

## Source layout

- Java package: `net.claustra01.tfie`
- Mod ID and resource namespace: `tfie`
- Main mod class: `src/main/java/net/claustra01/tfie/TFIE.java`
- Registrations and integrations: `src/main/java/net/claustra01/tfie/common/`
- Assets: `src/main/resources/assets/tfie/`
- Data pack resources: `src/main/resources/data/`

Keep registration classes small and grouped by registry responsibility. Use
NeoForge deferred registration and TFC registration helpers where TFC block or
block-entity behavior requires them.

## TFC and IE integration rules

- Reuse IE items, fluids, textures, and outputs when the integration represents
  an IE concept; do not register duplicate hemp seeds or fibers.
- Use TFC crop classes, climate ranges, nutrients, loot functions, tags, and
  interaction registration for crops intended to behave as TFC crops.
- Every custom TFC crop must have all required block variants, block entity
  support, climate data, loot tables, block tags, models, blockstates, and
  translations.
- Any fluid consumed by a TFC barrel recipe must be added to the appropriate
  TFC usability tags.
- If a compatibility recipe replaces an IE recipe, override the original
  recipe ID deliberately so the IE recipe cannot bypass the TFC progression.
- Use singular Minecraft 1.21 data directories such as `recipe/` and
  `loot_table/`.

## Resources and localization

- Provide only `en_us` translations for player-visible TFIE content unless the
  user explicitly requests another locale.
- Use existing TFC/IE models and textures where appropriate.
- Keep JSON readable and ensure all identifiers use the correct namespace.
- World-generation additions must be appended with tags using
  `"replace": false`.

## Scope safety

- Preserve unrelated user changes in the worktree.
- Do not change recipes, progression, world generation, or balance beyond the
  feature requested by the user.
- Avoid introducing configs, mixins, networking, or client-only code unless a
  concrete feature needs them.
