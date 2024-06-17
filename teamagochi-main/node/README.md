## Teamagochi Node

### Setup

Make sure you have cloned the repository with the `--recursive` flag to include the submodules. If you haven't, you can run `git submodule update --init --recursive` to get them.

### Development

1. Install VSCode
2. Install the C++ extension
3. Open the workspace starting from the root of the repository (Not this folder) to get the correct include paths
    1. This is also important for the linter and other settings to work correctly
4. Open a terminal at `node/code` and run `make compile-commands` to generate the compile commands file 
    1. This helps the C++ extension with intellisense

### Formatting

If you followed the development setup instructions, VSCode should automatically format the code based on the Google standards. 
Please make sure to follow the Google standards to keep the code consistent.

### Useful Commands

- `make compile-commands` - Generate the compile commands file
- `make build` - Build the project
- `make flash` - Flash the project onto the Feather
- `make term` - Open a serial terminal to the Feather
- `make clean` - Clean the build directory
