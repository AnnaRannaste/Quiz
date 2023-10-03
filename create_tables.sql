CREATE TABLE IF NOT EXISTS Topics (
                                      topic_id INT PRIMARY KEY AUTO_INCREMENT,
                                      name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Questions (
                                         question_id INT PRIMARY KEY AUTO_INCREMENT,
                                         topic_id INT NOT NULL,
                                         difficulty INT NOT NULL,
                                         content TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Responses (
                                         response_id INT PRIMARY KEY AUTO_INCREMENT,
                                         question_id INT NOT NULL,
                                         text TEXT NOT NULL,
                                         is_correct BOOLEAN NOT NULL,
                                         FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);






