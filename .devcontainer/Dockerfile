FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu

ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""

RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1

# Cria o usuário vscode, grupo e diretório home, se não existir
RUN id -u vscode 2>/dev/null || (\
    groupadd -g 1000 vscode && \
    useradd -m -u 1000 -g 1000 -d /workspaces -s /bin/bash vscode && \
    mkdir -p /workspaces && \
    chown vscode:vscode /workspaces)

# Garante que o usuário padrão seja vscode
USER vscode
