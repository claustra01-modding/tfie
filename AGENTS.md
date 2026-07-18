# AGENTS.md

## プロジェクト概要

TFIEは、Minecraft 1.21.1向けのTerraFirmaCraft（TFC）と
Immersive Engineering（IE）のNeoForge連携アドオンである。

要求された連携要素だけを実装し、参照元にある無関係な機能は移植しない。
主な参照実装は次のリポジトリとする。

- https://github.com/NMagpie/TFC-IE-Crossover/tree/1.21.x

参照元からコードやアセットを移植・改変した場合は、
`THIRD_PARTY_NOTICES.md`のMITライセンス表示を維持する。ビルドしたJARにも
この通知を必ず含める。

## このファイルの管理

- `AGENTS.md`は常にプロジェクトの最新状態と一致させる。
- パッケージ、依存関係、ディレクトリ構成、実装方針、検証方法を変更した場合は、
  同じ作業内でこのファイルも更新する。
- 新しい恒常的な実装上の注意点が判明した場合も、このファイルへ反映する。
- `README.md`へ機能一覧や作業履歴を継ぎ足さない。
- ユーザーから明示的に依頼されない限り、`README.md`は変更しない。

## ビルドと検証

- Java 21を使用する。
- 実装変更の検証には`./gradlew build`を使用する。
- ユーザーから明示的に依頼されない限り、`runClient`、`runServer`などで
  クライアントやサーバーを起動しない。
- 明確な必要性がない限り、データ生成を実行しない。
- 作業完了前に`git diff --check`を実行する。
- `build/`、`run/`、IDE設定、Gradleキャッシュなどの生成物をコミットしない。

## 依存関係

- NeoForge、Minecraft、TFC、IE、Patchouliのバージョンは
  `gradle.properties`で管理する。
- IE Garden ClocheのTFC二段作物描画にはDualCodecs 0.1.2を使用し、
  `jarJar`で成果物へ同梱する。
- mod依存関係は`src/main/templates/META-INF/neoforge.mods.toml`とGradleの
  設定で同期させる。
- ユーザーからアップグレードを依頼されない限り、参照元の1.21.xブランチと
  同じTFCおよびIEバージョンを優先する。
- Firmalife（mod ID `firmalife`）とArborFirmaCraft（mod ID `afc`）は任意依存とし、
  対象データには必ず`neoforge:mod_loaded`条件を付ける。これらを必須依存にしない。
- 任意modのJava APIへ直接依存せず、条件付きrecipeと既存IDによるデータ互換を
  基本とする。

## ソース構成

- Javaパッケージ: `net.claustra01.tfie`
- mod IDおよびリソース名前空間: `tfie`
- author: `claustra01`
- メインクラス: `src/main/java/net/claustra01/tfie/TFIE.java`
- 共通登録・連携処理: `src/main/java/net/claustra01/tfie/common/`
- クライアント専用処理: `src/main/java/net/claustra01/tfie/client/`
- server config: `src/main/java/net/claustra01/tfie/config/`
- Mixin: `src/main/java/net/claustra01/tfie/mixin/`
- Patchouliカスタムcomponent: `src/main/java/net/claustra01/tfie/compat/patchouli/`
- アセット: `src/main/resources/assets/tfie/`
- データパック: `src/main/resources/data/`

登録クラスはレジストリの責務ごとに小さく保つ。NeoForgeのDeferred Registerと、
必要に応じてTFCの登録ヘルパーを使用する。

## TFC・IE連携規約

- IE由来の概念には既存のIEアイテム、液体、テクスチャ、出力を再利用する。
  hemp seedやhemp fiberなどを重複登録しない。
- TFC作物として動作させる作物には、TFCの作物クラス、気候範囲、栄養、
  loot function、タグ、interaction登録を使用する。
- 独自TFC作物には、必要な全ブロック形態、block entity、気候データ、
  loot table、block tag、model、blockstate、翻訳を揃える。
- 透過テクスチャを使う作物ブロックは、クライアント側で`RenderType.cutout()`へ
  登録する。
- TFC barrelレシピで使用する液体は、対応するTFC usability tagへ追加する。
- IEの全流体は、TFC barrel、wooden bucket、red steel bucketのusability tagへ
  登録する。
- IEのfluid ingredient表示・照合では、Mixinを介してTFCの空容器候補を扱う。
- IE Crateへ格納可能かどうかはTFC item sizeを参照し、最大サイズはserver configで
  管理する。
- IE External Heaterはblock entity capabilityを介してTFC Crucibleを加熱し、
  消費FEと目標温度はserver configで管理する。
- IE Kinetic DynamoはTFCの`RotationSinkBlockEntity`として回転ネットワークへ接続し、
  発電倍率はserver configで管理する。
- IE HerbicideはTFC作物、野生作物、通常植物、grass、farmland、clay grassへ
  TFCに適した枯死・土壌変化を適用する。
- Garden Clocheでは全TFC作物recipe、全TFC土壌texture、二段作物用render functionを
  揃える。
- TFC全樹種およびAFC樹種のSawmill、TFC種のSqueezer、TFC作物・果実のFermenter、
  TFC fertilizer dataを維持する。
- 連携レシピでIE標準レシピを置き換える場合は、同じrecipe IDを明示的に
  上書きし、TFC進行を迂回できないようにする。
- 無効化用recipeは`tfie:empty` serializerと`neoforge:false`条件を使用する。
- IEの通常crafting置換と、その置換先となるTFC anvil recipeを対で保つ。
- Arc Furnaceのstructure NBTではTFC Fire BricksとTFC Steel Plated Blockを
  直接使用する。Engineer’s Manualのmultiblock描画も同じstructureを参照させ、
  実際の形成条件とGuide上の表示を一致させる。
- TFCの加熱・鉱石処理工程を迂回させないため、IE標準のCrusherとArc Furnaceにある
  vanilla鉱石倍化recipeを同じIDで無効化する。対象は両機械の`ore_*`、
  `raw_ore_*`、`raw_block_*`に加え、Crusherの`nether_gold`とArc Furnaceの
  `netherite_scrap`とする。
- Arc Furnaceのvanilla精錬相当であるIE標準`dust_*` recipeも同じIDで無効化する。
  alloy recipe、機械固有加工recipe、TFIEが追加するTFC向けrecipeまで一括で
  無効化しない。
- IE標準Arc Furnaceの`alloy_*` recipeは、TFCの合金比率を迂回するため全て同じIDで
  無効化する。
- Arc Furnace用のTFC合金recipeは、Bismuth Bronze、Black Bronze、Brass、Bronze、
  Rose Gold、Sterling Silver、Weak Steel、Weak Blue Steel、Weak Red Steelの9種類を
  `tfie:arcfurnace/alloy/`以下に揃え、出力にはTFC ingotを明示する。
- Arc Furnace用TFC合金recipeの整数比はTFC本体の許容範囲内にする。現在の比率は、
  Bismuth Bronze `Cu:Zn:Bi = 3:1:1`、Black Bronze `Cu:Ag:Au = 2:1:1`、
  Brass `Cu:Zn = 9:1`、Bronze `Cu:Sn = 9:1`、Rose Gold `Au:Cu = 3:1`、
  Sterling Silver `Ag:Cu = 3:2`、Weak Steel `Steel:Ni:Black Bronze = 6:2:2`、
  Weak Blue/Red Steelは`Black Steel:Steel:Alloy A:Alloy B = 10:5:2:3`とする。
- steel storage blockを作るTFIE Metal Press recipeの出力は
  `tfc:metal/block/steel`（Steel Plated Block）とする。Arc Furnaceの該当位置では
  IEの`c:storage_blocks/steel`一致より強い`BlockMatcher`判定を登録し、
  TFC Steel Plated Blockとの完全一致だけを許可する。
- Minecraft 1.21のデータディレクトリ名は`recipe/`、`loot_table/`のように
  単数形を使用する。
- Metal Pressの独自moldはスタック数を1とし、mold tagとTFC item sizeを定義する。
- TFC金属向けMetal Pressレシピでは、対象金属にTFC側の出力アイテムが実在する
  ことを確認する。未実装の独自IE金属や任意modの金属は追加しない。
- IE drill headのTFC連携では、Iron/Steelの標準クラフトを同じrecipe IDで無効化し、
  TFC double sheetを使う金床レシピへ置き換える。
- 独自drill headはIEの`DrillheadItem`を継承し、採掘tier、速度、範囲、耐久値、
  drill装着時textureを材質ごとに定義する。
- Quartz Geode、budding quartz、quartz cluster、quartz shardは実装しない。

## リソースと翻訳

- プレイヤーに表示されるTFIEコンテンツには`en_us`のみを用意する。
  別ロケールはユーザーから明示的に依頼された場合だけ追加する。
- 適切な既存TFC・IE modelおよびtextureを再利用する。
- JSONは読みやすく保ち、すべてのIDで正しい名前空間を使用する。
- world generation用タグへの追加では`"replace": false`を指定する。
- IE Engineer's ManualとTFC Field Guideには実装済み連携だけを記載し、Quartzや
  未移植の鉱石・金属機能を案内しない。
- IE Engineer's Manualの独自categoryには、`manual.<namespace>.<category>`形式の
  `en_us`翻訳キーを必ず用意する。
- Field Guideの三つ並び金床recipe表示には
  `net.claustra01.tfie.compat.patchouli.TriAnvilComponent`を使用する。

## 作業範囲と安全性

- worktreeにある無関係なユーザー変更を保持する。
- ユーザーが要求した機能を超えてrecipe、進行、world generation、バランスを
  変更しない。
- 具体的な機能上の必要がない限り、config、mixin、networking、client専用処理を
  追加しない。
