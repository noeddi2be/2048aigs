import React from 'react';

interface GameBoardProps {
  board: number[][];
}

const GameBoard: React.FC<GameBoardProps> = ({ board }) => {
  return (
    <div className="max-w-md mx-auto mt-5 p-4 flex">
      <div className="grid grid-cols-4 gap-2 bg-[#baa] shadow-stone-500 shadow-md p-3 rounded-lg">
        {board.map((row, rowIndex) => (
          row.map((tile, colIndex) => (
            <div
              key={`${rowIndex}-${colIndex}`}
              className="shadow-md shadow-black/20 rounded-lg"
            >
              <div
                className={`
                  w-20 h-20
                  flex items-center justify-center
                  rounded-lg
                  text-xl font-bold
                  transition-colors duration-200
                  shadow-inner shadow-neutral-100/40
                  ${getTileClass(tile)}
                `}
              >
                {tile || ''}
              </div>
            </div>
          ))
        ))}
      </div>
    </div>
  );
};

const getTileClass = (tile: number): string => {
  switch (tile) {
    case 0:
      return 'bg-[#dcb]';
    case 2:
      return 'bg-[#eee]';
    case 4:
      return 'bg-[#eec]';
    case 8:
      return 'bg-[#fb8] text-[#ffe]';
    case 16:
      return 'bg-[#f96] text-[#ffe]';
    case 32:
      return 'bg-[#f75] text-[#ffe]';
    case 64:
      return 'bg-[#f53] text-[#ffe]';
    case 128:
      return 'bg-[#ec7] text-[#ffe] text-[45px]';
    case 256:
      return 'bg-[#ec6] text-[#ffe] text-[45px]';
    case 512:
      return 'bg-[#ec5] text-[#ffe] text-[45px]';
    case 1024:
      return 'bg-[#ec3] text-[#fff] text-[35px]';
    case 2048:
      return 'bg-[#ec2] text-[#fff] text-[35px]';
    default:
      return 'bg-[#ec2] text-[#fff] text-[35px]';
  }
};

export default GameBoard;