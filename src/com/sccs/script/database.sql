DROP TABLE
  IF EXISTS enroll;

DROP TABLE
  IF EXISTS classroom;

DROP TYPE IF EXISTS day_of_the_week;

DROP TABLE
  IF EXISTS swimmingPool;

DROP TABLE
  IF EXISTS student;

DROP TABLE
  IF EXISTS teacher;

DROP TABLE
  IF EXISTS employee;

CREATE TABLE
  employee (
    employeeId SERIAL NOT NULL,
    employeeCpf VARCHAR(11) NOT NULL,
    employeeName VARCHAR(45) NOT NULL,
    employeeLogin VARCHAR(45) NOT NULL,
    employeePassword TEXT NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (employeeId),
    CONSTRAINT un_employee_cpf UNIQUE (employeeCpf),
    CONSTRAINT un_login UNIQUE (employeeLogin)
  );

CREATE TABLE
  teacher (
    teacherId SERIAL NOT NULL,
    teacherCpf VARCHAR(11) NOT NULL,
    teacherName VARCHAR(45) NOT NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (teacherId),
    CONSTRAINT un_teacher_cpf UNIQUE (teacherCpf)
  );

SET
  datestyle = ymd;

CREATE TABLE
  student (
    studentId SERIAL NOT NULL,
    studentCpf VARCHAR(11) NOT NULL,
    studentName VARCHAR(45) NOT NULL,
    birthDate DATE NOT NULL,
    age INTEGER NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (studentId),
    CONSTRAINT un_student_cpf UNIQUE (studentCpf)
  );

CREATE TABLE
  swimmingPool (
    poolNumber SERIAL NOT NULL,
    poolName VARCHAR(45) NOT NULL,
    averageAge INTEGER NOT NULL,
    maxCapacity INTEGER NOT NULL,
    lanesNumber INTEGER NOT NULL,
    poolWidth NUMERIC(6, 3) NOT NULL,
    poolLength NUMERIC(6, 3) NOT NULL,
    poolDepth NUMERIC(5, 3) NOT NULL,
    CONSTRAINT pk_swimmingPool PRIMARY KEY (poolNumber)
  );

CREATE TYPE day_of_the_week AS ENUM (
  'SUNDAY',
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY'
);

CREATE TABLE
  classroom (
    classroomId SERIAL NOT NULL,
    className VARCHAR(45) NOT NULL,
    usedPool INTEGER NOT NULL,
    vacanciesNumber INTEGER NOT NULL,
    enrollmentOpen BOOLEAN NOT NULL,
    teacher INTEGER NOT NULL,
    dayOfTheWeek day_of_the_week NOT NULL,
    CONSTRAINT pk_classroom PRIMARY KEY (classroomId),
    CONSTRAINT fk_swimmingPool_classroom_usedPool FOREIGN KEY (usedPool) REFERENCES swimmingPool(poolNumber),
    CONSTRAINT fk_teacher_classroom FOREIGN KEY (teacher) REFERENCES teacher(teacherId)
  );

CREATE TABLE
  enroll (
    enrollId SERIAL NOT NULL,
    classId INTEGER NOT NULL,
    employee INTEGER NOT NULL,
    student INTEGER NOT NULL,
    CONSTRAINT pk_enroll PRIMARY KEY (enrollId),
    CONSTRAINT fk_classroom_enroll FOREIGN KEY (classId) REFERENCES classroom(classroomId),
    CONSTRAINT fk_employee_enroll FOREIGN KEY (employee) REFERENCES employee(employeeId),
    CONSTRAINT fk_student_enroll FOREIGN KEY (student) REFERENCES student(studentId),
    CONSTRAINT un_student UNIQUE (student)
  );

INSERT INTO
  employee (
    employeeCpf,
    employeeName,
    employeeLogin,
    employeePassword
  )
VALUES
  (
    '00000000000',
    'Administrator',
    'admin',
    'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg='
  ),
  (
    '11111111111',
    'Fulano',
    'fulano',
    'VGBrdMminubqFpyF8OCy0gqDu7VYxPn3be1S1eFmH4Q='
  );

INSERT INTO
  teacher (teacherCpf, teacherName)
VALUES
  ('22222222222', 'Ciclano'),
  ('33333333333', 'Beltrano');

INSERT INTO
  student (studentCpf, studentName, birthDate, age)
VALUES
  ('33333333333', 'Euclaciano', '2022-06-06', 23),
  ('44444444444', 'Deltrano', '1996-02-29', 26);

INSERT INTO
  swimmingPool (
    poolName,
    averageAge,
    maxCapacity,
    lanesNumber,
    poolWidth,
    poolLength,
    poolDepth
  )
VALUES
  ('Olympic', 0, 10, 10, 50.0, 25.0, 3.05),
  ('Little', 10, 31, 45, 27.9, 13.8, 0.90);

INSERT INTO
  classroom (
    className,
    usedPool,
    vacanciesNumber,
    enrollmentOpen,
    teacher,
    dayOfTheWeek
  )
VALUES
  ('Competitive', 1, 10, false, 1, 'FRIDAY'),
  ('Beginner', 2, 45, true, 2, 'MONDAY');

INSERT INTO
  enroll (classId, employee, student)
VALUES
  (1, 1, 1),
  (2, 2, 2);