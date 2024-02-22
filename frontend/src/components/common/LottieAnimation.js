import React, { useEffect, useRef } from "react";
import lottie from "lottie-web";

export default function LottieAnimation({ animationPath }) {
  const animationContainer = useRef(null);

  useEffect(() => {
    const anim = lottie.loadAnimation({
      container: animationContainer.current,
      renderer: "svg",
      loop: true,
      autoplay: true,
      animationData: animationPath,
    });

    return () => anim.destroy();
  }, [animationPath]);

  return <div ref={animationContainer}></div>;
}
