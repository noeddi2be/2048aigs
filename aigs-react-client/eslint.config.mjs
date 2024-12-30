import globals from "globals";
import eslint from "@eslint/js";
import tseslint from "typescript-eslint";
import pluginReact from "eslint-plugin-react";
import htmlPlugin from "eslint-plugin-html";
import htmlEslint from "@html-eslint/eslint-plugin"; 
import htmlEslintParser from "@html-eslint/parser";
import prettierPlugin from 'eslint-plugin-prettier';
import eslintConfigPrettier from "eslint-config-prettier";


/** @type {import('eslint').Linter.Config[]} */
export default [
  {files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"]},
  {languageOptions: { globals: {...globals.browser, ...globals.node} }},
  {
    // recommended configuration included in the plugin
    ...htmlEslint.configs["flat/recommended"],
    files: ["**/*.html"],
    plugins: {
      "html": htmlPlugin,
      "@html-eslint": htmlEslint,
      "eslint-plugin-react": pluginReact,
      prettier: prettierPlugin,
    },
    languageOptions: {
      parser: htmlEslintParser,
    },
    rules: {
      ...htmlEslint.configs["flat/recommended"].rules, // Must be defined. If not, all recommended rules will be lost
      "@html-eslint/require-doctype": "error",
      'prettier/prettier': 'warn',
    },
    settings: {
      "html/html-extensions": [".html"],
      "html/xtml-extensions": [".html"],
      "html/javascript-mime-types": ["text/javascript", "text/jsx", "text/typescript", "text/tsx"],
    },
  },
  eslint.configs.recommended,
  ...tseslint.configs.recommended,
  pluginReact.configs.flat.recommended,
  pluginReact.configs.flat['jsx-runtime'],
  eslintConfigPrettier,
];
