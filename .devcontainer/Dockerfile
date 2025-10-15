FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu

ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""

ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""

RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# Install additional OS packages including sudo, graphviz, postgresql-client, and docker.io
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends docker.io graphviz postgresql-client sudo \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1

# Create vscode user, group and home directory if it doesn't exist
RUN id -u vscode 2>/dev/null || (\
    groupadd -g 1000 vscode && \
    useradd -m -u 1000 -g 1000 -d /workspaces -s /bin/bash vscode && \
    mkdir -p /workspaces && \
    chown vscode:vscode /workspaces) \
    && usermod -aG sudo vscode \
    && echo "vscode ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Enable colors in the terminal
ENV TERM=xterm-256color \
    COLORTERM=truecolor \
    FORCE_COLOR=1

# Configure .bashrc to enable colors and git branch in prompt
RUN echo 'export TERM=xterm-256color' >> /workspaces/.bashrc \
    && echo 'export COLORTERM=truecolor' >> /workspaces/.bashrc \
    && echo 'export FORCE_COLOR=1' >> /workspaces/.bashrc \
    && echo 'alias ls="ls --color=auto"' >> /workspaces/.bashrc \
    && echo 'alias grep="grep --color=auto"' >> /workspaces/.bashrc \
    && echo '' >> /workspaces/.bashrc \
    && echo '# Git branch in prompt' >> /workspaces/.bashrc \
    && echo 'parse_git_branch() {' >> /workspaces/.bashrc \
    && echo '    git branch 2> /dev/null | sed -e "/^[^*]/d" -e "s/* \(.*\)/ (\1)/"' >> /workspaces/.bashrc \
    && echo '}' >> /workspaces/.bashrc \
    && echo 'export PS1="\[\033[01;32m\]\u\[\033[00m\]:\[\033[01;34m\]\w\[\033[01;31m\]\$(parse_git_branch)\[\033[00m\]\$ "' >> /workspaces/.bashrc \
    && chown vscode:vscode /workspaces/.bashrc

# Ensure that the default user is vscode
USER vscode
