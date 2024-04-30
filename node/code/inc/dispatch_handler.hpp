/**
 * @brief DispatchHandler is an abstract class that receives events.
 * @author Tom <git@annsann.eu>
 */

using namespace std;

/**
 * DispatchHandler is an abstract class that receives events.
 * @see Dispatcher
 * If you want to receive events, you should inherit from this class and
 * implement the handleEvent function. handleData is optional, but you should
 * implement it if you want to receive data from the heightsensor.
 */
class DispatchHandler {
 public:
  int counter = 0;
};
