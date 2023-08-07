# sistema-seu
📖 SEU: Sistema Educacional Unificado

## Dependências

```bash
sudo apt install -y make build-essential libssl-dev zlib1g-dev \
libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev \
libncursesw5-dev xz-utils tk-dev libffi-dev liblzma-dev python3-openssl

curl https://pyenv.run | bash


cat << 'EOF' >> ~/.bashrc
export PATH="$HOME/.pyenv/bin:$PATH"
eval "$(pyenv init -)"
eval "$(pyenv virtualenv-init -)"
EOF
exec "$SHELL"

curl -sSL https://install.python-poetry.org | python -
poetry config virtualenvs.in-project true
```
