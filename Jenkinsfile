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
                    
                    # 저장소가 존재하면 최신 코드 가져오기
                    if [ -d "safety_signature/.git" ]; then
                        echo "✅ Git 저장소가 존재합니다. 최신 코드 가져오기."
                        cd safety_signature
                        git reset --hard origin/${REPO_BRANCH}
                        git pull origin ${REPO_BRANCH}
                    else
                        echo "⚠️  Git 저장소가 존재하지 않습니다. 클론을 수행합니다."
                        git clone -b ${REPO_BRANCH} git@github.com:Ohjinseo2022/safety_signature.git safety_signature
                        echo "⚠️  클론을 수행완료."
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
                    cd /home/ojsadmin/jenkins/safety_signature

                    echo "🔄 최신 코드 가져오기..."
                    git pull origin ${REPO_BRANCH}

                    echo "🛑 기존 컨테이너 종료..."
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose down

                    echo "⚙️  컨테이너 빌드..."
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose build

                    echo "🚀 컨테이너 실행..."
                    echo '${SUDO_PASSWORD}' | sudo -S docker-compose up -d
                    EOF
                    '''
                }
            }
        }
    }
}
