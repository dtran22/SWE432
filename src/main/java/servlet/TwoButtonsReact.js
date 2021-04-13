// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/import
import React, { useState, useCallback, useEffect, useRef } from "react"; // https://reactjs.org/docs/hooks-intro.html
import ReactDOM from "react-dom";

const numberOnly = (value, fallbackValue) =>
  isNaN(value) ? fallbackValue : value;
// function numberOnly(value, fallbackValue) {
//   if (isNaN(value)) {
//     return fallbackValue;
//   } else {
//     return value;
//   }
// }

function TwoButtons(props) {
  const { title } = props; // const title = props.title;
  const [firstValue, setFirstValue] = useState(""); // const stateAndSetter= useState(""); const firstValue = stateAndSetter[0], setFirstValue= stateAndSetter[1];
  const [secondValue, setSecondValue] = useState("");
  const [result, setResult] = useState("");
  const [disableOperations, setDisableOperations] = useState(true);

  const handleFirstValueChange = useCallback(
    (event) => {
      // function (event){
      setFirstValue((currentFirstValue) => {
        const nextFirstValue = event.target.value;
        return numberOnly(nextFirstValue, currentFirstValue);
      });
    },
    [] // no need to include dependency setFirstValue: setters do not mutate
  );

  const handleSecondValueChange = useCallback(
    (event) => {
      setSecondValue(event.target.value);
    },
    [] // no need to include dependency setSecondValue: setters do not mutate
  );

  const handleAddChange = useCallback(
    (event) => {
      event.preventDefault(); // prevents form submit action
      setResult(+firstValue + +secondValue); // setResult(parseFloat(firstValue) + parseFloat(secondValue))
    },
    [firstValue, secondValue, setResult]
  );

  const handleSubstractChange = useCallback(
    (event) => {
      event.preventDefault(); // prevents form submit action
      setResult(+firstValue - +secondValue);
    },
    [firstValue, secondValue, setResult]
  );

  const handleMultiplicationChange = useCallback(
    (event) => {
      event.preventDefault(); // prevents form submit action
      setResult(+firstValue * +secondValue);
    },
    [firstValue, secondValue, setResult]
  );

  const handleResetChange = useCallback(
    (event) => {
      event.preventDefault(); // prevents form submit action
      setFirstValue("");
      setSecondValue("");
      setResult("");
    },
    [] // no need to include dependencies setFirstValue, setSecondValue, or setResult: setters do not mutate
  );

  const focusedElementRef = useRef();

  useEffect(() => {
    // focusedElementRef.current.focus();
  }, []);

  useEffect(
    () => {
      let nextDisableOperations = false;
      if (isNaN(firstValue) || isNaN(secondValue)) {
        nextDisableOperations = true;
      }

      disableOperations !== nextDisableOperations &&
        setDisableOperations(nextDisableOperations);
    },
    [disableOperations, firstValue, secondValue] // need to include firstValue and secondValue: values do mutate
  );

  return (
    <div>
      <p>{title}</p>
      <form
        method="post"
        action="https://cs.gmu.edu:8443/offutt/servlet/formHandler"
      >
        <p>
          First Value:
          <input
            ref={focusedElementRef}
            name="firstValue"
            value={firstValue}
            onChange={handleFirstValueChange}
            placeholder="0"
          />
        </p>
        <p>
          Second Value:
          <input
            name="secondValue"
            value={secondValue}
            onChange={handleSecondValueChange}
            placeholder="0"
          />
        </p>
        <p>
          Result: <input value={result} readOnly placeholder="0" />
        </p>
        <button
          type="submit"
          name="operation"
          value="add"
          disabled={disableOperations}
          onClick={handleAddChange}
        >
          Add
        </button>
        <button
          type="submit"
          name="operation"
          value="sub"
          onClick={handleSubstractChange}
        >
          Substract
        </button>
        <button
          type="submit"
          name="operation"
          value="multiply"
          onClick={handleMultiplicationChange}
        >
          Multiply
        </button>
        <button onClick={handleResetChange}>Reset</button>
      </form>
    </div>
  );
}

ReactDOM.render(
  <TwoButtons
    title={
      "A simple example that demonstrates how to operate with multiple submit buttons."
    }
  />,
  document.querySelector("#root")
);

// If you got here, please check the Material UI version of this example:
// https://codesandbox.io/s/swe-432-react-two-buttons-example-mui-yohyi
