pipeline {
    agent any

    environment {
        SERVER_USER = "ojsadmin"
        SERVER_IP = "124.63.21.91"
        REPO_BRANCH = "master"  // 저장소 브랜치
        SUDO_PASSWORD = credentials('sudo-password')
    }

    stages {
        stage('Checkout Code') {
            steps {
                sshagent (credentials: ['github-ssh-key']) {  // 기존 키 사용
                    sh '''
                    ssh -p 10000 ${SERVER_USER}@${SERVER_IP} << 'EOF'
                    cd /home/ojsadmin/jenkins
                    if [ -d "safety_signature" ]; then
                        cd safety_signature
                        git reset --hard origin/${REPO_BRANCH}
                        git pull origin ${REPO_BRANCH}
                    else
                        git clone -b ${REPO_BRANCH} git@github.com:Ohjinseo2022/safety_signature.git
                    fi
                    EOF
                    '''
                }
            }
        }

        stage('Build & Deploy with Docker') {
            steps {
                sshagent (credentials: ['server-ssh-key']) {
                    sh '''
                    ssh -p 10000 ${SERVER_USER}@${SERVER_IP} << 'EOF'
                    cd /home/ojsadmin/jenkins
                    git pull origin master
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose down
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose build
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose up -d
                    EOF
                    '''
                }
            }
        }
    }
}
