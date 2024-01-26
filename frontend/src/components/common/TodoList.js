//연습을 위해 넣어둔 페이지 (안쓰는겁니다)

import React, { useState, useEffect, useCallback } from "react";
import Sortable from "sortablejs";
import {
  getToDoList,
  addToDoList,
  updateToDoList,
  deleteToDoList,
} from "../api/api";
import styles from "./ToDoList.module.css";
import classNames from "classnames";

const TodoList = ({ userId }) => {
  const [todoList, setTodoList] = useState([]);
  const [newTodo, setNewTodo] = useState("");
  const [num, setNum] = useState(0);

  const handleUpdateTodo = useCallback(
    (id, state, todo) => {
      updateToDoList({
        todoId: id,
        userId: userId,
        todoState: state,
        todo: todo,
      });
    },
    [userId]
  );
  const handleAddTodo = () => {
    if (newTodo.trim() !== "") {
      addToDoList({ userId: userId, todoState: 0, todo: newTodo });
      setTodoList([
        ...todoList,
        {
          todoId: num,
          userId: userId,
          todoState: 0,
          todo: newTodo,
        },
      ]);
      setNewTodo("");
      setNum(num + 1);
    }
  };
  const handleDeleteTodo = (deletedTodo) => {
    // todoList에서 삭제
    const updatedTodoList = todoList.filter(
      (item) => item.todoId !== deletedTodo.todoId
    );
    setTodoList(updatedTodoList);

    // inProgressList에서 삭제
    const updatedInProgressList = inProgressList.filter(
      (item) => item.todoId !== deletedTodo.todoId
    );
    setInProgressList(updatedInProgressList);

    // completedList에서 삭제
    const updatedCompletedList = completedList.filter(
      (item) => item.todoId !== deletedTodo.todoId
    );
    setCompletedList(updatedCompletedList);

    // 서버에서도 삭제
    deleteToDoList(deletedTodo.todoId);
  };

  useEffect(() => {
    const todoSortable = new Sortable(document.getElementById("todo-list"), {
      group: "shared-list",
      swapThreshold: 1,
      animation: 150,
      onEnd: function (evt) {
        // 안전성 검사 추가
        const item = evt.to.outerHTML.charAt(8);
        const state = item === "t" ? 0 : item === "i" ? 1 : 2;
        console.log("드래그한곳:", item);
        if (evt.item.children[0] && evt.item.children[1]) {
          const todo = evt.item.children[0].textContent;
          const id = parseInt(evt.item.children[1].textContent);
          console.log("드래그 중인 아이템의 텍스트 내용:", todo, id);
          handleUpdateTodo(id, state, todo);
        } else {
          console.error("드래그 중인 아이템이 올바르게 설정되지 않았습니다.");
        }
      },
    });

    return () => {
      todoSortable.destroy();
    };
  }, [todoList, handleUpdateTodo]);

  return (
    <div className={styles.box}>
      <div className={styles.list}>
        <h2>할일 목록</h2>
        <ul id="todo-list" className={styles.ul}>
          {todoList.map((item) => (
            <li
              key={`todo-${item.todoId}`}
              className={classNames(styles.li, styles.before)}
            >
              <div className={styles.libox}>
                <div>{item.todo}</div>
                {/* <img
                  onClick={() => handleDeleteTodo(item)}
                  src={minus}
                  alt="minus"
                  width="20px"
                  height="20px"
                  className={styles.img}
                ></img> */}
              </div>
              <div className={styles.none}>{item.todoId}</div>
            </li>
          ))}
        </ul>
        {/* <div className={styles.add}>
          <input
            type="text"
            value={newTodo}
            onChange={(e) => setNewTodo(e.target.value)}
            placeholder="새로운 할일 추가"
          />
          <img
            onClick={handleAddTodo}
            className={styles.button}
            src={plus}
            alt="plus"
            width="25px"
          ></img>
        </div> */}
      </div>
    </div>
  );
};

export default TodoList;
