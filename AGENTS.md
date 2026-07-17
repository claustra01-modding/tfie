# AGENTS.md

## プロジェクト概要

TFIEは、Minecraft 1.21.1向けのTerraFirmaCraft（TFC）と
Immersive Engineering（IE）の小規模なNeoForge連携アドオンである。

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
- mod依存関係は`src/main/templates/META-INF/neoforge.mods.toml`とGradleの
  設定で同期させる。
- ユーザーからアップグレードを依頼されない限り、参照元の1.21.xブランチと
  同じTFCおよびIEバージョンを優先する。
- 参照元が使用しているという理由だけで、Firmalifeなどの任意modを追加しない。

## ソース構成

- Javaパッケージ: `net.claustra01.tfie`
- mod IDおよびリソース名前空間: `tfie`
- author: `claustra01`
- メインクラス: `src/main/java/net/claustra01/tfie/TFIE.java`
- 共通登録・連携処理: `src/main/java/net/claustra01/tfie/common/`
- クライアント専用処理: `src/main/java/net/claustra01/tfie/client/`
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
- 連携レシピでIE標準レシピを置き換える場合は、同じrecipe IDを明示的に
  上書きし、TFC進行を迂回できないようにする。
- Minecraft 1.21のデータディレクトリ名は`recipe/`、`loot_table/`のように
  単数形を使用する。
- Metal Pressの独自moldはスタック数を1とし、mold tagとTFC item sizeを定義する。
- TFC金属向けMetal Pressレシピでは、対象金属にTFC側の出力アイテムが実在する
  ことを確認する。未実装の独自IE金属や任意modの金属は追加しない。
- IE drill headのTFC連携では、Iron/Steelの標準クラフトを同じrecipe IDで無効化し、
  TFC double sheetを使う金床レシピへ置き換える。
- 独自drill headはIEの`DrillheadItem`を継承し、採掘tier、速度、範囲、耐久値、
  drill装着時textureを材質ごとに定義する。

## リソースと翻訳

- プレイヤーに表示されるTFIEコンテンツには`en_us`のみを用意する。
  別ロケールはユーザーから明示的に依頼された場合だけ追加する。
- 適切な既存TFC・IE modelおよびtextureを再利用する。
- JSONは読みやすく保ち、すべてのIDで正しい名前空間を使用する。
- world generation用タグへの追加では`"replace": false`を指定する。

## 作業範囲と安全性

- worktreeにある無関係なユーザー変更を保持する。
- ユーザーが要求した機能を超えてrecipe、進行、world generation、バランスを
  変更しない。
- 具体的な機能上の必要がない限り、config、mixin、networking、client専用処理を
  追加しない。
